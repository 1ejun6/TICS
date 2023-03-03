<?php
require "conn.php";

// Get the student ID from the query string
$StudentID = $_GET['StudentID'];

// Build the SQL query to retrieve the data
$sql = "SELECT g.GameID, g.GameDate, g.GameStartTime, g.GameEndTime, g.GameTicCount FROM students s
        JOIN game g ON s.StudentID = g.StudentID
        WHERE s.StudentID = '$StudentID'";

// Execute the query
$result = mysqli_query($conn, $sql);

// Check if there are any results
if (mysqli_num_rows($result) > 0) {
    // Fetch the data and output it as a JSON array
    $rows = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $rows[] = $row;
    }
    echo json_encode($rows);
} else {
    // Output an error message if no results were found
    echo "No data found for student ID $StudentID";
}

// Close the database connection
mysqli_close($conn);
?>