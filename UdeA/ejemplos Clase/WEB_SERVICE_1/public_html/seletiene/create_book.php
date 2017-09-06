<?php
if($_SERVER['REQUEST_METHOD'] == 'POST'){
  $libro = $_POST['libro'];
  $autor = $_POST['autor'];
  $descripcion = $_POST['descripcion'];

  $sql = "insert into libros(libro,autor,descripcion) values ('$libro','$autor','$descripcion')";

  require_once('db_connect.php');

  if (mysqli_query($con,$sql)) echo "libro creado";
  else    echo "ERROR: el libro no fue creado";
  
  mysqli_close($con);

}
 ?>
