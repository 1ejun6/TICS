<?php
require "conn.php";

if (isset($_GET['StudentID'])) {
    $studentID = $_GET['StudentID'];

    // Retrieve student details based on StudentID
    $studentQuery = "SELECT * FROM students WHERE StudentID = ?";
    $studentStmt = $conn->prepare($studentQuery);
    $studentStmt->bind_param("i", $studentID);
    $studentStmt->execute();
    $studentResult = $studentStmt->get_result();

    if ($studentResult->num_rows > 0) {
        $row = $studentResult->fetch_assoc();
        $student = array(
            'StudentID' => $row['StudentID'],
            'StudentName' => $row['StudentName'],
            'StudentParentName' => $row['StudentParentName'],
            'StudentParentNo' => $row['StudentParentNo']
        );
        echo json_encode($student);
    } else {
        echo "Student not found";
    }

    $conn->close();
} else {
    echo "StudentID is required";
}
?>