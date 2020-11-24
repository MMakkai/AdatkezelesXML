<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
<html> 
<body>
  <h2>Autók</h2>
  <table border="1">
    <tr bgcolor="#9acd32">
      <th style="text-align:left">Rendszám</th>
    </tr>
    <xsl:for-each select="autok">
    <xsl:sort select="auto/tulaj/varos">
    <tr>
    	
    </tr>
    </xsl:sort>
    </xsl:for-each>
  </table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>