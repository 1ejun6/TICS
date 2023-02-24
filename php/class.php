<?php
require "conn.php";

$query = "SELECT ClassID, ClassName FROM class";
$result = mysqli_query($conn, $query);

if (mysqli_num_rows($result) > 0) {
    $response["class"] = array();
    while ($row = mysqli_fetch_assoc($result)) {
        $class = array();
        $class["ClassID"] = $row["ClassID"];
        $class["ClassName"] = $row["ClassName"];
        array_push($response["class"], $class);
    }
    echo json_encode($response);
} else {
    echo "No class found";
}

// Another query to get the ClassName of a specific ClassID
if (isset($_GET["class_id"])) {
    $class_id = $_GET["class_id"];
    $query = "SELECT ClassName FROM class WHERE ClassID = '$class_id'";
    $result = mysqli_query($conn, $query);

    if (mysqli_num_rows($result) > 0) {
        $row = mysqli_fetch_assoc($result);
        $class_name = $row["ClassName"];
        echo $class_name;
    } else {
        echo "No class found";
    }
}

mysqli_close($conn);
?>