<!DOCTYPE html>
<html>
<head>
    <title>Tum4World</title>
    <meta charset="utf-8">
    <%--inlusione di librerie esterne per la creazione dell'istogramma--%>
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script src="http://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>
    <%--tema applicato all'istogramma--%>
    <script src="${pageContext.request.contextPath}/themes/brand-light.js"></script>

    <script src="${pageContext.request.contextPath}/JAVASCRIPT/Amministratore.js"></script>
</head>
<body>
<div class="contenutiTutti">
    <div id="dashboard">
        <ul>
            <h1>OPZIONI</h1>
            <li><button onclick="getDataUtenti('tutti')"> Utenti</button></li>
            <li><button onclick="getDataUtenti('simpatizzante')"> Simpatizzanti</button></li>
            <li><button onclick="getDataUtenti('aderente')"> Aderenti</button></li>
            <li><button onclick="visualizzaVisite()"> Dati Visite</button></li>
            <li><button onclick="visualizzaDonazioni()"> Donazioni</button></li>
        </ul>
    </div>

    <div id="areaVisualizzazione">
        <h1>
            <br>
            Area personale amministratore.
            <br>
        </h1>
    </div>
</div>

</body>
</html>

