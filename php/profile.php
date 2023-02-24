<?php
require "conn.php";
$name = $_POST["TeacherName"];
$email = $_POST["TeacherEmail"];
$class = intval($_POST["ClassID"]);

$query = "UPDATE teachers SET TeacherEmail='$email', ClassID='$class' WHERE TeacherName='$name'";
$query_run = mysqli_query($conn, $query);

if ($query_run) {

} else {
	echo ("error");
}
?>