<?php
require "conn.php";
$studentID = $_GET['StudentID'];

$sql = "SELECT TicCount, TicDate, TicTime, Picture FROM Tic WHERE StudentID = '$studentID' ORDER BY TicDate DESC, TicTime DESC";

$result = $conn->query($sql);
$tics = array();
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $tic = array(
            'TicCount' => $row['TicCount'],
            'TicDate' => $row['TicDate'],
            'TicTime' => $row['TicTime'],
            'Picture' => base64_encode($row['Picture'])
        );
        array_push($tics, $tic);
    }
}
echo json_encode($tics);

$conn->close();
?>