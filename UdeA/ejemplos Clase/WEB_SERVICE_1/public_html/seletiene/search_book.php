<?php 
    $libro = $_GET['libro'];
    
    require_once('db_connect.php');
    
    $sql = "select * from libros where libro='$libro'";
    
    $r= mysqli_query($con, $sql);
    
    $result = array();
    $row = mysqli_fetch_array($r);
    
    array_push($result,array(
        "id"=>$row['id'],
        "libro"=>$row['libro'],
        "autor"=>$row['autor'],
        "descripcion"=>$row['descripcion']
    ));
    
    echo json_encode(array('result'=>$result));
    
    mysqli_close($con);
?>