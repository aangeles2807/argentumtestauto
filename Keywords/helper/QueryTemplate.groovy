package helper

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.stringtemplate.v4.ST

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable

public class QueryTemplate {

	public static final ST afiliadoMPP = new ST(

	"SELECT * FROM (\n" +
	"	SELECT \n" +
	"		  crt.crtnumcon contrato \n" +
	"		, ben.natide \n" +
	"		, pro.procod \n" +
	"		, pro.pronom \n" +
	"		, mpl.mplcod \n" +
	"		, mpl.mplnom \n" +
	"		, tpl.tipplacod \n" +
	"		, tpl.tipplanom \n" +
	"		, par.plaparcod \n" +
	"		, par.plaparnom \n" +
	"		, best.afibenestcod \n" +
	"		, best.afibenestnom \n" +
	"		, nat.natpriape \n" +
	"		, nat.natsegape \n" +
	"		, nat.natprinom \n" +
	"		, nat.natsegnom \n" +
	"		, nat.NATSEX \n" +
	"		, nat.NATNUMIDE \n" +
	//"	 	, ben.crtcon \n" +
	//"		, ben.sbccon \n" +
	//"		, ben.bencon \n" +
	"	FROM \n" +
	"		  tabcrt crt \n" +
	" 		, tabsbc sbc \n" +
	"		, tabben ben \n" +
	"		, tabafibenest best \n" +
	"		, tabnat nat \n" +
	"		, tabmpl mpl \n" +
	"		, tabtippla tpl \n" +
	"		, tabpro pro \n" +
	"		, tabplapar par \n" +
	"	WHERE 1 = 1 \n" +
	"	AND mpl.mpltippla = tpl.tipplacod \n" +
	"	AND pro.procod = mpl.procod \n" +
	"	AND crt.crtcon = sbc.crtcon \n" +
	"	AND sbc.sbccon = ben.sbccon \n" +
	"	AND crt.crtcon = ben.crtcon \n" +
	"	AND nat.natide = ben.natide \n" +
	"	AND best.afibenestcod = ben.afibenestcod \n" +
	"	AND par.plaparcod = ben.plaparcod \n" +
	"	AND mpl.mplcod = ben.mplcod \n" +
	"	AND pro.procod = mpl.procod \n" +
	"	AND crt.crtcon = sbc.crtcon \n" +
	"	AND sbc.sbccon = ben.sbccon \n" +
	"	AND crt.crtcon = ben.crtcon \n" +
	"	AND nat.natide = ben.natide \n" +
	"	AND best.afibenestcod = ben.afibenestcod \n" +
	"	AND par.plaparcod = ben.plaparcod \n" +
	"	AND mpl.mplcod = ben.mplcod \n" +
	"	AND <fechaAutorizacion> between crt.crtinivig AND crt.crtfinvig \n" + // Parametro
	"	<conditions> \n" + // Parametro
	"	ORDER BY DBMS_RANDOM.RANDOM \n" +
	") \n" +
	"WHERE rownum = 1"
	);

	public static final ST afiliadoPBS = new ST(

	"SELECT * FROM ( \n" +
	"	SELECT \n" +
	"		  con.connumcon contrato \n" +
	"		, hij.natide \n" +
	"		, pro.procod \n" +
	"		, pro.pronom \n" +
	"		, mpl.mplcod \n" +
	"		, mpl.mplnom \n" +
	"		, tpl.tipplacod \n" +
	"		, tpl.tipplanom \n" +
	"		, par.plaparcod \n" +
	"		, par.plaparnom \n" +
	"		, hest.afihijestcod \n" +
	"		, hest.afihijestnom \n" +
	"		, nat.natpriape \n" +
	"		, nat.natsegape \n" +
	"		, nat.natprinom \n" +
	"		, nat.natsegnom \n" +
	"		, nat.NATSEX \n" +
	"		, nat.NATNUMIDE \n" +
	//"	 	, ben.crtcon \n" +
	//"		, ben.sbccon \n" +
	//"		, ben.bencon \n" +
	"	FROM \n" +
	"		  tabcon con \n" +
	"		, tabhij hij \n" +
	"		, tabafihijest hest \n" +
	"		, tabnat nat \n" +
	"		, tabmpl mpl \n" +
	"		, tabtippla tpl \n" +
	"		, tabpro pro \n" +
	"		, tabplapar par \n" +
	"	WHERE 1 = 1 \n" +
	"	AND mpl.mpltippla = tpl.tipplacod \n" +
	"	AND pro.procod = mpl.procod \n" +
	"	AND con.concon = hij.concon \n" +
	"	AND nat.natide = hij.natide \n" +
	"	AND hest.afihijestcod = hij.afihijestcod \n" +
	"	AND par.plaparcod = hij.plaparcod \n" +
	"	AND mpl.mplcod = hij.mplcod \n" +
	"	AND <fechaAutorizacion> between con.coninivig AND con.confinvig \n" + // Parametro
	"	<conditions> \n" + // Parametro
	"	ORDER BY DBMS_RANDOM.RANDOM \n" +
	") \n" +
	"WHERE rownum = 1"
	);

	public static final ST afiliadoMPPoPBS = new ST(

	"SELECT * FROM ( \n" +

	"	SELECT * FROM ( \n" +

	"		<afiliadoMPP> \n" +
	"UNION ALL \n" +
	"		<afiliadoPBS> \n" +

	"	)" +

	"	ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum = 1"
	);

	public static final ST prestadorServicio = new ST(

	"SELECT * FROM ( \n" +
	"	SELECT \n" +
	"		  ser.IPSCODSUP \n" +
	"		, ser.MPLCOD \n" +
	"		, ser.SERIPSCOD \n" +
	"		, ips.IPSNOM \n" +
	"		, tips.SERIPSNOM \n" +
	"  		, SUC.IPSSUCCOD \n"+
	"	FROM tabconser ser \n" +
	"	JOIN TABCONIPS ips \n" +
	"	ON ips.IPSCODSUP = ser.IPSCODSUP \n" +
	"	JOIN TABSERIPS tips \n" +
	"	ON tips.SERIPSCOD = ser.SERIPSCOD \n" +
	"	LEFT OUTER JOIN TABCONIPSSUC SUC \n"+
	" 	ON IPS.IPSCODSUP = SUC.IPSCODSUP \n"+
	"	WHERE 1 = 1 \n" +
	"	AND trim(mplcod) LIKE ('<codigoCobertura>') \n" + // Parametro
	"	AND trim(ser.sercon) = 'S' \n" +
	"	AND trim(SERIPS_SEXO) IN ('A', '<generoAfiliado>') \n" + // Parametro
	"	AND trim(ips.ipsestado) = '<estadoPrestador>' \n" + // Parametro
	"	AND ips.tipovincod = '06' \n" +
	"	<servicioConsulta> \n" + // Parametro
	"	ORDER BY DBMS_RANDOM.RANDOM \n" +
	") \n" +
	"WHERE rownum = 1"
	);

	public static final ST diagnostico = new ST(

	"SELECT * FROM ( \n" +
	"	SELECT \n" +
	"		  DIA_DIA_CODIGO \n" +
	"		, DIA_DIA_DESCRIPCIO \n" +
	"	FROM \n" +
	"		  DIA_DIAGNOS \n" +
	"	WHERE \n" +
	"		  trim(dia_dia_sexo) in ('A', '<generoAfiliado>') \n" + // Parametro
	"	ORDER BY DBMS_RANDOM.RANDOM \n" +
	") \n" +
	"WHERE rownum = 1"
	);

	public static ST procedimientoPorPrestador = new ST(

	"SELECT * FROM ( \n" +
	"	SELECT \n" +
	"		  trim(pre.pre_pre_codigo) codigo_prestacion \n" +
	"		, trim(pre.pre_pre_descripcio) descripcion_prestacion \n" +
	"	FROM tabconips ips \n" +
	"	JOIN pre_prestacion pre \n" +
	"	ON 1 = 1 \n" +
	"	AND pre.pre_pre_vigencia = 1 \n" +
	"	AND TO_NUMBER (pre.pre_pre_nivel) \\<= TO_NUMBER (ips.nivatecod) \n" +
	"	JOIN tabmpl mpl \n" +
	"	ON 1 = 1 \n" +
	"	AND 1 = CASE WHEN mpl.procod = '01' \n" +
	"	AND EXISTS ( \n" +
	"		SELECT 1 FROM \n" +
	"			tabpreser preser \n" +
	"		WHERE 1 = 1 " +
	"		AND trim(preser.seripscod) = '<codigoServicioPrestadorSalud>' \n" + // Parametro
	"		AND preser.pre_pre_codigo = pre.pre_pre_codigo \n" +
	"	) \n" +
	"	THEN 1 WHEN mpl.procod = '03' THEN 1 ELSE 0 END \n" +
	"	JOIN tabmpr mpr ON 1 = 1 \n" +
	"	AND mpr.procod = mpl.procod \n" +
	"	AND mpr.mplcod = mpl.mplcod \n" +
	"	AND mpr.pre_pre_codigo = pre.pre_pre_codigo \n" +
	"	AND <fechaAutorizacion> BETWEEN mpr.mprperini \n" +
	"	AND mpr.mprperfin \n" +
	"	JOIN con_convenio con \n" +
	"	ON 1 = 1 \n" +
	"	AND con.ipscodsup = ips.ipscodsup \n" +
	"	AND con.procod = mpl.procod \n" +
	"	AND con.mplcod = mpl.mplcod \n" +
	"	AND <fechaAutorizacion> BETWEEN con.confechaleg \n" +
	"	AND con.confechavenci \n" +
	"	JOIN tabconpag pag \n" +
	"	ON 1 = 1 \n" +
	"	AND pag.conpagcap = 'N' \n" +
	"	AND pag.conpagcod = con.conpagcod \n" +
	"	JOIN tabconcvn cvn \n" +
	"	ON 1 = 1 \n" +
	"	AND cvn.ipscodsup = ips.ipscodsup \n" +
	"	AND cvn.procod = mpl.procod \n" +
	"	AND cvn.mplcod = mpl.mplcod \n" +
	"	AND cvn.tipodocconvcodigo = con.tipodocconvcodigo \n" +
	"	AND cvn.con_con_codigo = con.con_con_codigo \n" +
	"	AND <fechaAutorizacion> BETWEEN cvn.cvnperini \n" +
	"	AND cvn.cvnperfin \n" +
	"	AND cvn.tipmodcod = '1' \n" +
	"	JOIN tabconmcv mcv \n" +
	"	ON 1 = 1 \n" +
	"	AND mcv.tipmodcod = cvn.tipmodcod \n" +
	"	AND mcv.mcvmod = CASE \n" +
	"	WHEN TRIM (cvn.cvnmod) IS NULL THEN \n" +
	"		cvn.cvnsin \n" +
	"	ELSE \n" +
	"		cvn.cvnmod \n" +
	"	END \n" +
	"	AND <fechaAutorizacion> BETWEEN mcv.mcvperini \n" +
	"	AND mcv.mcvperfin \n" +
	"	AND mcv.pre_pre_codigo = pre.pre_pre_codigo \n" +
	"	AND mcv.tipatecod = mpr.tipatecod \n" +
	"	AND NOT mcv.mcvpor > 100 \n" +
	"	JOIN con_tarifas tar \n" +
	"	ON 1 = 1 \n" +
	"	AND tar.tipotarifcodigo = mcv.tipotarifcodigo \n" +
	"	AND <fechaAutorizacion> BETWEEN tar.pre_tar_periodoini \n" +
	"	AND tar.pre_tar_periodofin \n" +
	"	JOIN net_contarprepre net \n" +
	"	ON 1 = 1 \n" +
	"	AND net.tipotarifcodigo = mcv.tipotarifcodigo \n" +
	"	AND net.pre_pre_codigo = pre.pre_pre_codigo \n" +
	"	AND net.pre_tar_periodoini = tar.pre_tar_periodoini \n" +
	"	JOIN tabtipate ate \n" +
	"	ON ate.tipatecod = mpr.tipatecod \n" +
	"	JOIN tabtippla tpl on tpl.tipplacod = mpl.mpltippla \n" +
	"	WHERE 1 = 1 \n" +
	//"	AND REGEXP_LIKE(SUBSTR(PRE_PRE_TIPO, 1, 1), '^\\d+(\\.\\d+)?\$') \n" +
	"	AND pre.pre_pre_quirurgico = 0 \n" +
	"	AND Trim(pre_pre_sexo) in ('A', '<generoAfiliado>') \n" + // SEXO
	"	AND trim(ips.ipscodsup) = '<codigoPrestadorSalud>' \n" + // CODIGO DE PRESTADOR
	"	AND trim(mpl.mplcod) = '<codigoCobertura>' \n" + // CODIGO DE PLAN
	"	AND 1 = CASE WHEN EXISTS ( \n" +
	"		SELECT 1 FROM \n" +
	"			tabcontiphab tiphab \n" +
	"		WHERE 1 = 1 \n" +
	"		AND tiphab.pre_pre_codigo = pre.pre_pre_codigo \n" +
	"	) \n" +
	"	OR ( \n" +
	"		pre.pre_pre_descripcio LIKE '%HAB%' \n" +
	"		AND SUBSTR (pre.pre_pre_codigo, 1, 2) = 'S1' \n" +
	"	) \n" +
	"	THEN 0 ELSE 1 END \n" +
	"	ORDER BY DBMS_RANDOM.RANDOM \n" +
	") \n" +
	"WHERE rownum = 1"
	);

}
