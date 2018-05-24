<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>CheapAir - Página Principal</title>
    <link rel="stylesheet" type="text/css" href="resultados.css"/>
  </head>

  <body>
 
  	<!-- Cabecera de la página -->
 	<header>
 		<h1>CheapAir</h1>
 	</header>
 	
 	<h3 id="result">
 		Resultados de vuelos
 	</h3>
 
 	<!-- Viajes de ida -->
 	<section>
 	<c:if test="${travelResult.travelsIda.size() > 0}">
 	 <h4>Viajes de ida:</h4>
 	</c:if> 
 	
 	<c:forEach var="viaje" items="${travelResult.travelsIda}">
 			<article>
	 				<p><c:out value="${viaje.fechaSalida.date}" />/<c:out value="${viaje.fechaSalida.month + 1}" />/<c:out value="${viaje.fechaSalida.year + 1900}" />  
	 				<c:out value="${viaje.fechaSalida.hours}" />:<c:out value="${viaje.fechaSalida.minutes}" /> <c:out value="${viaje.origen.nombre}" /> - <c:out value="${viaje.destino.nombre}" /> 
	 				<c:out value="${viaje.fechaLlegada.date}" />/<c:out value="${viaje.fechaLlegada.month + 1}" />/<c:out value="${viaje.fechaLlegada.year + 1900}" />  
	 				<c:out value="${viaje.fechaLlegada.hours}" />:<c:out value="${viaje.fechaLlegada.minutes}" /></p>
	 			
	 			<br>
	 			
	 			<c:if test="${!empty viaje.escala}">
	 				<p>Escala: <c:out value="${viaje.escala}" /> </p>
	 			</c:if>
	 			
	 			<br>
	 			
	 			<p>Duración: <c:out value="${viaje.escala}" /> </p>
	 			
	 			<br>
	 			
	 			<p>Precio: <c:out value="${viaje.precio}" /> € </p>
	 			
	 			<br>
	 			
	 			<form action="${viaje.url}">
	 				<input type="submit" class="boton" value="Reservar" />
	 			</form>
 			</article>
 	</c:forEach>
 	</section>
 	
 	<!-- Viajes de vuelta -->
 	<section>
 	<c:if test="${travelResult.travelsVuelta.size() > 0}">
 	 <h4>Viajes de vuelta:</h4>
 	</c:if> 
 	
 	<c:forEach var="viaje" items="${travelResult.travelsVuelta}">
 			<article>
	 				<p><c:out value="${viaje.fechaSalida.date}" />/<c:out value="${viaje.fechaSalida.month + 1}" />/<c:out value="${viaje.fechaSalida.year + 1900}" />  
	 				<c:out value="${viaje.fechaSalida.hours}" />:<c:out value="${viaje.fechaSalida.minutes}" /> <c:out value="${viaje.origen.nombre}" /> - <c:out value="${viaje.destino.nombre}" /> 
	 				<c:out value="${viaje.fechaLlegada.date}" />/<c:out value="${viaje.fechaLlegada.month + 1}" />/<c:out value="${viaje.fechaLlegada.year + 1900}" />  
	 				<c:out value="${viaje.fechaLlegada.hours}" />:<c:out value="${viaje.fechaLlegada.minutes}" /></p>
	 			
	 			<br>
	 			
	 			<c:if test="${!empty viaje.escala}">
	 				<p>Escala: <c:out value="${viaje.escala}" /> </p>
	 			</c:if>
	 			
	 			<br>
	 			
	 			<p>Duración: <c:out value="${viaje.escala}" /> </p>
	 			
	 			<br>
	 			
	 			<p>Precio: <c:out value="${viaje.precio}" /> € </p>
	 			
	 			<br>
	 			
	 			<form action="${viaje.url}">
	 				<input type="submit" class="boton" value="Reservar" />
	 			</form>
 			</article>
 	</c:forEach>
 	</section>
 	
 	<!-- Pie de página con nuestros nombres -->
 	<footer>
 		<h4>Proyecto hecho por: Alberto Silvestre Montes Linares · Sergio Romera Guzmán</h4>
 	</footer>	

  </body>
</html>