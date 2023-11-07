<?php 
   $r = $_POST['chieurong'];
   $d = $_POST['chieudai'];
   $dt = $r*$d ;
   $cv =($r+$d)*2;
   echo "Chu vi: ".$cv.";Dien tich: ".$dt
?>