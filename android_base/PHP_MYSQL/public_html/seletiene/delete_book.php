<?php 
    $libro = $_GET['libro'];
    
    require_once('db_connect.php');
    
    $sql = "delete from libros where libro='$libro'";
    
    if (mysqli_query($con,$sql)) echo "libro Eliminado";
    else    echo "ERROR:libro no eliminado";
    
    mysqli_close($con);
?>