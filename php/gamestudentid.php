<?php
require "conn.php";

// Query to fetch all student IDs from the medical_history table
$query = "SELECT DISTINCT StudentID FROM game";

// Execute the query
$result = mysqli_query($conn, $query);

// Check if there are any results
if (mysqli_num_rows($result) > 0) {
    // Create an array to store the results
    $response = array();

    // Loop through the results and add each StudentID to the array
    while ($row = mysqli_fetch_assoc($result)) {
        array_push($response, $row['StudentID']);
    }

    // Send the array as a JSON response
    echo json_encode($response);
} else {
    // Send an error message if no results were found
    echo "No results found";
}

// Close the database connection
mysqli_close($conn);
?>