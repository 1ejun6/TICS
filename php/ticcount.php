<?php
require "conn.php";
$StudentID = $_GET['StudentID'];
$today = date("Y-m-d");
// query to get tic count for student on today's date
$stmt = mysqli_query($conn, "SELECT COUNT(*) AS TicCount FROM tic WHERE StudentID = '" . $StudentID . "' AND TicDate = '" . $today . "'");
$result = mysqli_fetch_assoc($stmt);
$todaysTicCount = $result['TicCount'];
// if there are no tics on today's date, return 0
if ($todaysTicCount == 0) {
    $response = array('TicCount' => 0, 'TicDate' => $today);
} else {
    $response = array('TicCount' => (int) $todaysTicCount, 'TicDate' => $today);
}
echo json_encode($response);
?>