package com.jzargo.services;

import com.jzargo.shared.common.DiscountType;
import com.jzargo.shared.model.DiscountDto;
import com.jzargo.entity.Discount;
import com.jzargo.entity.Product;
import com.jzargo.exceptions.DataNotFoundException;
import com.jzargo.exceptions.DiscountException;
import com.jzargo.mapper.DiscountReadMapper;
import com.jzargo.repository.CouponRepository;
import com.jzargo.repository.ProductDiscountRepository;
import com.jzargo.repository.ProductRepository;
import com.jzargo.repository.SeasonDiscountRepository;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService{
    private final ProductRepository productRepository;
    private final SeasonDiscountRepository seasonDiscountRepository;
    private final DiscountReadMapper discountReadMapper;
    private final ProductDiscountRepository productDiscountRepository;
    private final CouponRepository couponRepository;

    public DiscountServiceImpl(ProductRepository productRepository, SeasonDiscountRepository seasonDiscountRepository, DiscountReadMapper discountReadMapper, ProductDiscountRepository productDiscountRepository, CouponRepository couponRepository) {
        this.seasonDiscountRepository = seasonDiscountRepository;
        this.productRepository = productRepository;
        this.discountReadMapper = discountReadMapper;
        this.productDiscountRepository = productDiscountRepository;
        this.couponRepository = couponRepository;
    }

    @SneakyThrows
    @Override
    public double calculateDiscount(Double price, int quantity, DiscountDto dto) {
        if (dto == null || dto== null) {
            throw new DiscountException("Discount cannot be null");
        }
        return dto.getDiscountType().equals(DiscountType.PERCENT)?
                (( 100 - dto.getDiscount() )/ 100) * price * quantity :
                price * quantity;
    }

    @SneakyThrows
    @Override
    public DiscountDto getMaxDiscountForProduct(Integer productId, int quantity) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new DataNotFoundException("Product with " + productId + "not found"));

        Discount discount = product.getDiscount();

        List<Discount> season = seasonDiscountRepository.findAllByCategoryId(product.getCategory().getId())
                .stream()
                .map(sd -> (Discount) sd)
                .collect(Collectors.toList());

        season.add(discount);
        return season.stream().max(
                        Comparator.comparingDouble(JDiscount ->
                            calculateDiscount(product.getPrice(), quantity, discountReadMapper.map(JDiscount))
                        )
                )
                .map(discountReadMapper::map)
                .orElseThrow(() -> new DataNotFoundException("No season discount found for product " + productId));
    }

    @Override
    public boolean isDiscountValid(DiscountDto discount) {
        if (discount == null) {
            return false;
        }
        if (discount.getStartDate() == null || discount.getEndDate() == null ||
                discount.getStartDate().isAfter(discount.getEndDate())) {

            return false;
        }

        if (discount.getMinOrderPrice() != null && discount.getMinOrderPrice() < 0) {
            return false;
        }

        if (discount.getDiscountType().equals(DiscountType.PERCENT) &&
                (discount.getDiscount() < 0 || discount.getDiscount() > 100)) {
            return false;
        }

        return !discount.getDiscountType().equals(DiscountType.FIXED) ||
                (discount.getDiscount() >= 0 && discount.getDiscount() <= discount.getMinOrderPrice());
    }

    @Override
    public void updateDiscountValidity() {
        seasonDiscountRepository.findAll().stream().filter(seasonDiscount ->
                seasonDiscount.getEndDate().isBefore(LocalDate.now()))
                .forEach(seasonDiscountRepository::delete);

        productDiscountRepository.findAll().stream().filter(productDiscount ->
                productDiscount.getEndDate().isBefore(LocalDate.now()))
                .forEach(productDiscountRepository::delete);

        couponRepository.findAll().stream().filter(coupon ->
                coupon.getEndDate().isBefore(LocalDate.now())
                ).forEach(couponRepository::delete);
    }
}
