<?php
require "conn.php";
$name = $_POST['TeacherName'];
$password = $_POST['TeacherPassword'];
$result = array();
if ($conn) {
	$sql = "select * from teachers where TeacherName LIKE '" . $name . "'";
	$res = mysqli_query($conn, $sql);
	if (mysqli_num_rows($res) != 0) {
		$row = mysqli_fetch_assoc($res);
		if ($name == $row['TeacherName'] && $password == $row['TeacherPassword'] && $row['TeacherEmail'] != null) {
			try {
				$apikey = bin2hex(random_bytes(23));
			} catch (Exception $e) {
				$apikey = bin2hex(uniqid($name, true));
			}
			$sqlUpdate = "update teachers set apikey='" . $apikey . "' where TeacherName ='" . $name . "'";
			if (mysqli_query($conn, $sqlUpdate)) {
				$result = array(
					"status" => "relogin",
					"message" => "login success",
					"TeacherID" => $row['TeacherID'],
					"TeacherName" => $row['TeacherName'],
					"apikey" => $row['apikey'],
					"TeacherEmail" => $row['TeacherEmail']
				);
			} else
				$result = array("status" => "failed", "message" => "login failed name is $name and pswd is $password");
		} else if ($name == $row['TeacherName'] && $password == $row['TeacherPassword'] && $row['TeacherEmail'] == null) {
			try {
				$apikey = bin2hex(random_bytes(23));
			} catch (Exception $e) {
				$apikey = bin2hex(uniqid($name, true));
			}
			$sqlUpdate = "update teachers set apikey='" . $apikey . "' where TeacherName ='" . $name . "'";
			if (mysqli_query($conn, $sqlUpdate)) {
				$result = array(
					"status" => "success",
					"message" => "login success",
					"TeacherID" => $row['TeacherID'],
					"TeacherName" => $row['TeacherName'],
					"apikey" => $row['apikey'],
					"TeacherEmail" => $row['TeacherEmail']
				);
			}
		} else
			$result = array("status" => "failed", "message" => "Wrong Username or Password! Try Again!");
	} else
		$result = array("status" => "failed", "message" => "There is no such user");
} else
	$result = array("status" => "failed", "message" => "database connection failed");
echo json_encode($result, JSON_PRETTY_PRINT);
?>