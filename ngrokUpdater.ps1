# Check if ngrok is run
ngrok http 8080

$ngrokApiUrl = "http://localhost:4040/api/tunnels"
$response = Invoke-RestMethod -Uri $ngrokApiUrl
$ngrokUrl = $response.tunnels[0].public_url

$workingPath = Get-Location
$envFilePath = Join-Path -Path $workingPath -ChildPath ".env"

$envContent = Get-Content -Path $envFilePath

$ngrokLine = "NGROK_URL=$ngrokUrl"

$ngrokExists = $envContent -match "^NGROK_URL="

if ($ngrokExists) {
    $envContent = $envContent -replace "^NGROK_URL=.*", $ngrokLine
} else {
    $envContent += "`r`n" + $ngrokLine
}

$envContent | Set-Content -Path $envFilePath

Write-Host "NGROK_URL updated in .env: $ngrokUrl"

