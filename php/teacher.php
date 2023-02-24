<?php
require "conn.php";

if (isset($_GET['TeacherName'])) {
    $TeacherName = $_GET['TeacherName'];

    // Retrieve teacher details based on TeacherName
    $teacherQuery = "SELECT * FROM teachers WHERE TeacherName = ?";
    $teacherStmt = $conn->prepare($teacherQuery);
    $teacherStmt->bind_param("s", $TeacherName);
    $teacherStmt->execute();
    $teacherResult = $teacherStmt->get_result();

    if ($teacherResult->num_rows > 0) {
        $row = $teacherResult->fetch_assoc();
        $teacher = array(
            'TeacherID' => $row['TeacherID'],
            'TeacherName' => $row['TeacherName'],
            'TeacherEmail' => $row['TeacherEmail'],
            'TeacherPassword' => $row['TeacherPassword'],
            'ClassID' => $row['ClassID']
        );
        echo json_encode($teacher);
    } else {
        echo "Teacher not found";
    }

    $conn->close();
} else {
    echo "TeacherName is required";
}
?>