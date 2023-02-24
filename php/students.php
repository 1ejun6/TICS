<?php
require "conn.php";

if (isset($_GET['className'])) {
    $className = $_GET['className'];

    // Retrieve the ClassID based on the ClassName
    $classQuery = "SELECT ClassID FROM class WHERE ClassName = ?";
    $classStmt = $conn->prepare($classQuery);
    $classStmt->bind_param("s", $className);
    $classStmt->execute();
    $classResult = $classStmt->get_result();

    if ($classResult->num_rows > 0) {
        $classRow = $classResult->fetch_assoc();
        $classID = $classRow['ClassID'];

        // Use the ClassID to retrieve the students
        $studentQuery = "SELECT * FROM students WHERE ClassID = ?";
        $studentStmt = $conn->prepare($studentQuery);
        $studentStmt->bind_param("i", $classID);
        $studentStmt->execute();
        $studentResult = $studentStmt->get_result();

        if ($studentResult->num_rows > 0) {
            $students = array();

            while ($row = $studentResult->fetch_assoc()) {
                $student = array(
                    'StudentID' => $row['StudentID'],
                    'StudentName' => $row['StudentName']
                );

                array_push($students, $student);
            }

            echo json_encode($students);
        } else {
            echo "No students found";
        }
    } else {
        echo "Class not found";
    }

    $conn->close();
} else {
    echo "ClassName is required";
}
?>