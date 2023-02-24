<?php
require "conn.php";
$query = "SELECT students.StudentName, students.StudentID, students.StudentParentName, students.StudentParentNo, students.ClassID, class.ClassName FROM students LEFT JOIN class ON students.ClassID = class.ClassID";
$result = mysqli_query($conn, $query);
$response = array();
if (!$result) {
    die("Query failed: " . mysqli_error($conn));
}
while ($row = mysqli_fetch_array($result)) {
    if ($row['ClassID'] == null) {
        $className = "Unknown";
    } else {
        $className = $row['ClassName'];
    }
    array_push($response, array("StudentName" => $row["StudentName"], "StudentID" => $row["StudentID"], "StudentParentName" => $row["StudentParentName"], "StudentParentNo" => $row["StudentParentNo"], "ClassName" => $className));
}
echo json_encode($response);
?>