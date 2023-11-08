<?php 
$soA = 5;
$soB = 6;

$thongbao = "tong 2 so $soA va $soB = ". ($soA + $soB);

echo "$thongbao <br>";

$txt1 = "Learn PHP";
$txt2 = "W3Schools.com";
$x = 5;
$y = 4;

echo "<h2>" . $txt1 . "</h2>";
echo "Study PHP at " . $txt2 . "<br>";
echo $x + $y;

$t = date("H");

if ($t < "20") {
  echo "Have a good day! <br>";
} else {
  echo "Have a good night! <br>";
}

$cars = array("Volvo", "BMW", "Toyota");
echo "I like " . $cars[0] . ", " . $cars[1] . " and " . $cars[2] . ". <br>";

function familyName($fname) {
    echo "$fname Refsnes. <br>";
  }
  
  familyName("Jani");
  familyName("Hege");
  familyName("Stale");
  familyName("Kai Jim");
  familyName("Borge");

?>