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
 	<c:forEach var="viajeida" items="${tr.travelsida}">
 		<section>
 			<h4>Viajes de ida:</h4>
 		<c:forEach var="viaje" items="${viajeida}">
 			<article class="viaje">
 				<c:forEach var="vuelo" items="${viaje.vuelos}">
	 				<p><c:out value="${viaje.fechasalida.date}" />/<c:out value="${viaje.fechasalida.month + 1}" />/<c:out value="${viaje.fechasalida.year + 1900}" />  
	 				<c:out value="${viaje.fechasalida.hours}" />:<c:out value="${viaje.fechasalida.minutes}" /> <c:out value="${vuelo.origen.nombre}" /> - <c:out value="${vuelo.destino.nombre}" /> 
	 				<c:out value="${viaje.fechallegada.date}" />/<c:out value="${viaje.fechallegada.month + 1}" />/<c:out value="${viaje.fechallegada.year + 1900}" />  
	 				<c:out value="${viaje.fechallegada.hours}" />:<c:out value="${viaje.fechallegada.minutes}" /></p>
	 			</c:forEach>
	 			
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
 	</c:forEach>
 	
 	<!-- Viajes de vuelta -->
 	<c:forEach var="viajevuelta" items="${tr.travelsvuelta}">
 		<section>
 			<h4>Viajes de vuelta:</h4>
 		<c:forEach var="viaje" items="${viajevuelta}">
 			<article class="viaje">
 				<c:forEach var="vuelo" items="${viaje.vuelos}">
	 				<p><c:out value="${viaje.fechasalida.date}" />/<c:out value="${viaje.fechasalida.month + 1}" />/<c:out value="${viaje.fechasalida.year + 1900}" />  
	 				<c:out value="${viaje.fechasalida.hours}" />:<c:out value="${viaje.fechasalida.minutes}" /> <c:out value="${vuelo.origen.nombre}" /> - <c:out value="${vuelo.destino.nombre}" /> 
	 				<c:out value="${viaje.fechallegada.date}" />/<c:out value="${viaje.fechallegada.month + 1}" />/<c:out value="${viaje.fechallegada.year + 1900}" />  
	 				<c:out value="${viaje.fechallegada.hours}" />:<c:out value="${viaje.fechallegada.minutes}" /></p>
	 			</c:forEach>
	 			
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
 	</c:forEach>
 	
 	<!-- Pie de página con nuestros nombres -->
 	<footer>
 		<h4>Proyecto hecho por: Alberto Silvestre Montes Linares · Sergio Romera Guzmán</h4>
 	</footer>	

  </body>
</html>