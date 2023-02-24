<?php
require "conn.php";
$name = $_POST["TeacherName"];
$password = $_POST["TeacherPassword"];

$mysqli_query = "INSERT INTO teachers VALUES (NULL,'$name',NULL,'$password',NULL,NULL);";

$result = mysqli_query($conn, $mysqli_query);

$result = mysqli_query($conn, "SET @num := 0;");
$result = mysqli_query($conn, "UPDATE teachers SET TeacherID = @num := (@num+1)");
$result = mysqli_query($conn, "ALTER TABLE teachers AUTO_INCREMENT =1");

if ($result) {
} else {
    print("NOT Successful");
}

?>