<?php 

$a = $_POST["a"];
$b = $_POST["b"];
$c = $_POST["c"];

$delta = pow($b ,2)-4 * $a * $c;

$ketqua = "";
if($a == 0){
    if($b == 0){
        $ketqua = "PT vo so nghiem";
    }else{
       $x1 = -$c/$b;
       $ketqua = "PT trinh co nghiem la".$x1;
    }
   
} else{
    if($delta <0){
        $ketqua = "PT vo nghiem";
    }else{
        if($delta == 0){
            $x1 = -$b/(2*$a);
            $ketqua = "phuong trinh cos nghiem kep". $x1;
        }else{
            $x1 = ((-$b+sqrt($delta))/(2*$a));
            $x2 = ((-$b-sqrt($delta))/(2*$a));

            $ketqua = "phuong trinh co 2 nghiem phan biet : x1 = ".$x1. "x2 = ".$x2;
        }
    }
}
echo "ket qua :". $ketqua;
?>