<?php
if($_SERVER['REQUEST_METHOD'] == 'POST'){
  $libro = $_POST['libro'];
  $autor = $_POST['autor'];
  $descripcion = $_POST['descripcion'];

  $sql = "update libros set autor='$autor',descripcion='$descripcion' where libro='$libro'";

  require_once('db_connect.php');

  if (mysqli_query($con,$sql)) echo "libro actualizado";
  else    echo "ERROR: el libro no actualizado";
  
  mysqli_close($con);

}

?>