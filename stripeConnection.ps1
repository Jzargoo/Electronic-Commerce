$process = Start-Process stripe -ArgumentList "listen", "--forward-to", "http://localhost:8080/api/webhook" -PassThru -Wait
$output = $process.StandardOutput.ReadToEnd()
$secretLine = $output | Select-String -Pattern "Your webhook secret is"
$secret = $secretLine -replace "Your webhook secret is ", ""
$envFilePath = ".env"

if (Test-Path $envFilePath) {
    $envContent = Get-Content $envFilePath
    $envContent = $envContent -replace '^WEBHOOK_SECRET=.*$', "WEBHOOK_SECRET=$secret"
    Set-Content $envFilePath $envContent
} else {
    "WEBHOOK_SECRET=$secret" | Out-File $envFilePath
}

Write-Host "Stripe webhook secret added to .env: $secret"

