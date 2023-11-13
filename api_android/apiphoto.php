<?php
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, OPTIONS');
header('Access-Control-Allow-Headers: *');

header('Content-Type: application/json');

// Đọc dữ liệu từ tệp JSON
$jsonData = file_get_contents('contact.json');
$contacts = json_decode($jsonData, true);

// Trả về dữ liệu JSON
echo json_encode($contacts);
?>
