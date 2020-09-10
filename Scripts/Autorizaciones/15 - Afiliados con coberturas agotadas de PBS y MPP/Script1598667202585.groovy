import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import helper.CommonAction
import helper.DBConnection
import helper.Keyword
import internal.GlobalVariable as GlobalVariable

// Condiciones de servicios
List<String> servicios = Arrays.asList(
	Keyword.SERVICIO_TERAPIAS_FISICAS.value,
	Keyword.SERVICIO_SONOGRAFIA.value,
	Keyword.SERVICIO_RAYOS_X.value,
	Keyword.SERVICIO_PATOLOGIA.value,
	Keyword.SERVICIO_ODONTOLOGIA.value,
	Keyword.SERVICIO_LABORATORIO.value,
	Keyword.SERVICIO_ESTUDIOS_ESPECIALES.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_2.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_1.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_2.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_3.value,
	Keyword.SERVICIO_EMERGENCIA_TRIAGE_4.value,
	Keyword.SERVICIO_PSIQUIATRIA.value
);

// Valor aleatorio
int valorAleatorio = CommonAction.getUniqueIntance().getRandomNumber( servicios.size() );

Map<String, String> queryResult = null;
DBConnection dbConnection = DBConnection.getDBConnectionUniqueIntance();

String afiliadoConCoberturaAgotada = 
"SELECT * FROM  (                                 																													\n" +
"	SELECT                                                                                                                                                          \n" +
"		natide                                                                                                                                                      \n" +
"	FROM                                                                                                                                                            \n" +
"		(                                                                                                                                                           \n" +
"			SELECT                                                                                                                                                  \n" +
"				'NORMAL' tipo,                                                                                                                                      \n" +
"				natide,                                                                                                                                             \n" +
"				tgt.grutopdes,                                                                                                                                      \n" +
"				tca.conacunom,                                                                                                                                      \n" +
"				afi1.topcueafifecini,                                                                                                                               \n" +
"				afi1.topcueafifecfin,                                                                                                                               \n" +
"				afi1.topcueafican,                                                                                                                                  \n" +
"				afi1.topcuearival,                                                                                                                                  \n" +
"				afi1.topcueafided,                                                                                                                                  \n" +
"				afi1.topcueafivalgmmexc,                                                                                                                            \n" +
"				afi1.topcuevalcon,                                                                                                                                  \n" +
"				nvl(topcuearival,0) + nvl(topcuevalcon,0) AS topcuevalsum,                                                                                          \n" +
"				afi1.procod,                                                                                                                                        \n" +
"				pro.pronom,                                                                                                                                         \n" +
"				CASE vig.topgrutop                                                                                                                                  \n" +
"						WHEN 30   THEN vig.topval                                                                                                                   \n" +
"						ELSE vig.topval                                                                                                                             \n" +
"					END                                                                                                                                             \n" +
"				AS topval,                                                                                                                                          \n" +
"				CASE vig.topgrutop                                                                                                                                  \n" +
"						WHEN 30                                                       THEN                                                                          \n" +
"							CASE                                                                                                                                    \n" +
"								WHEN vig.topval - ( nvl(topcuearival,0) + nvl(topcuevalcon,0) ) > 0 THEN vig.topval - ( nvl(topcuearival,0) + nvl(topcuevalcon,0) ) \n" +
"								ELSE 0                                                                                                                              \n" +
"							END                                                                                                                                     \n" +
"						ELSE                                                                                                                                        \n" +
"							CASE                                                                                                                                    \n" +
"								WHEN vig.topval - ( nvl(topcuearival,0) + nvl(topcuevalcon,0) ) > 0 THEN vig.topval - ( nvl(topcuearival,0) + nvl(topcuevalcon,0) ) \n" +
"								ELSE 0                                                                                                                              \n" +
"							END                                                                                                                                     \n" +
"					END                                                                                                                                             \n" +
"				AS topvaldif,                                                                                                                                       \n" +
"				' ' xval                                                                                                                                            \n" +
"			FROM                                                                                                                                                    \n" +
"				tabtopcueafi1 afi1,                                                                                                                                 \n" +
"				tabconacu tca,                                                                                                                                      \n" +
"				tabgrutop tgt,                                                                                                                                      \n" +
"				tabtopvig vig,                                                                                                                                      \n" +
"				tabmpl mpl,                                                                                                                                         \n" +
"				tabpro pro                                                                                                                                          \n" +
"			WHERE                                                                                                                                                   \n" +
"				afi1.topvigcon = vig.topvigcon                                                                                                                      \n" +
"				AND   vig.conacucod = tca.conacucod                                                                                                                 \n" +
"				AND   vig.topgrutop = tgt.grutopcod                                                                                                                 \n" +
"				AND   afi1.mplcod = mpl.mplcod                                                                                                                      \n" +
"				AND   afi1.procod = pro.procod                                                                                                                      \n" +
"				AND   SYSDATE BETWEEN afi1.topcueafifecini AND afi1.topcueafifecfin                                                                                 \n" +
"				AND   tgt.grutopcod = 7                                                                                                                             \n" +
"		) x                                                                                                                                                         \n" +
"	WHERE                                                                                                                                                           \n" +
"		topvaldif = 0                                                                                                                                               \n" +
"		AND   NOT EXISTS (                                                                                                                                          \n" +
"			SELECT                                                                                                                                                  \n" +
"				'x'                                                                                                                                                 \n" +
"			FROM                                                                                                                                                    \n" +
"				tabben ben2                                                                                                                                         \n" +
"			WHERE                                                                                                                                                   \n" +
"				ben2.natide = x.natide                                                                                                                              \n" +
"		) ORDER BY DBMS_RANDOM.RANDOM) WHERE rownum = 1                                                                                                                                                           \n";

// Obtenemos el String Template con la(s) llave(s) y valor(es) agregado(s)
// Ejecutamos la consulta y obtenemos los resultados
queryResult = dbConnection.executeQueryAndGetResult("afiliadoConCoberturaAgotada", afiliadoConCoberturaAgotada);

WS.callTestCase(findTestCase('Comun/ProcesoAutorizacion'), [
	// Querys
	'ejecutarQueryCapturaAfiliadoMPP' : false,
	'ejecutarQueryCapturaAfiliadoPBS' : true,
	'ejecutarQueryCapturaAfiliadoMPPoPBS' : false,
	'ejecutarQueryPrestadorServicio' : true,
	'ejecutarQueryPrestadorFueradeRed' : false,
	'ejecutarQueryServicioConPrestacion' : false,
	// APIs
	'consultarApiAfiliado' : true,
	'consultarApiAfiliadoCasoPositivo' : true,
	'consultarApiPrestadorSalud' : true,
	'consultarApiPrestadorSaludCasoPositivo' : true,
	'consultarApiPrestadorSaludServicios' : true,
	'consultarApiPrestadorSaludServiciosCasoPositivo' : true,
	// Querys
	'ejecutarQueryDoctor' : false,
	// APIs
	'consultarApiPrestadorSaludDoctores' : false,
	'consultarApiPrestadorSaludDoctoresCasoPositivo' : false,
	'consultarApiAutorizacionPortalValidarCobertura' : true,
	'consultarApiAutorizacionPortalValidarCoberturaCasoPositivo' : true,
	'consultarApiAutorizacionPortalCamposRequeridos' : true,
	'consultarApiAutorizacionPortalCamposRequeridosCasoPositivo' : true,
	// Querys
	'ejecutarQueryDiagnostico' : true,
	// APIs
	'consultarApiConsultarDiagnosticos' : true,
	'consultarApiConsultarDiagnosticosCasoPositivo' : true,
	'consultarApiAutorizacionPortalIngresar' : true,
	'consultarApiAutorizacionPortalIngresarCasoPositivo' : true,
	// Querys
	'ejecutarQueryProcedimientoPorPrestador' : true,
	'ejecutarQueryPrestacionNoContratada' : false,
	// APIs
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientos' : true,
	'consultarApiAutorizacionPortalPrestadorSaludProcedimientosCasoPositivo' : true,
	'consultarApiConsultarProcedimientos' : true,
	'consultarApiConsultarProcedimientosCasoPositivo' : true,
	'consultarApiAutorizacionPortalTarifaProcedimiento' : true,
	'consultarApiAutorizacionPortalTarifaProcedimientoCasoPositivo' : true,
	'consultarApiAutorizacionPortalAutorizar' : true,
	'consultarApiAutorizacionPortalAutorizarCasoPositivo' : true,
	'consultarApiAutorizacionPortalAnular' : false,
	'consultarApiAutorizacionPortalAnularCasoPositivo' : false,
	// Condiciones de los Querys
	'condicionAfiliadoPBS' : String.valueOf("AND trim(hij.natide) = '${queryResult.get("NATIDE")}' "),
	//'condicionAfiliadoPBS' : Keyword.AFILIADO_PBS_ACTIVO.value,
	'servicioConsulta' : servicios.get(valorAleatorio)], FailureHandling.STOP_ON_FAILURE);