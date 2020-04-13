package testservlet;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import no.hvl.dat109.Servlet.NermasteLeveringsstad;
import no.hvl.dat109.hjelpeklasser.Melding;
import no.hvl.dat109.hjelpeklasser.Meldingstype;

public class NermasteLeveringsstadTest {
	
	 @Mock
	 HttpServletRequest request;
	 @Mock
	 HttpServletResponse response;

	 NermasteLeveringsstad servlet;
	 Gson gson;
	 
    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	gson = new GsonBuilder()
		        .excludeFieldsWithoutExposeAnnotation()
		        .create();
    	servlet = new NermasteLeveringsstad();
    }

    @Test
    public void hentAvfallsplassarUtanforRekkeviddeGirfeil() throws ServletException, IOException {
        when(request.getParameter("latitude")).thenReturn("63.9147");
        when(request.getParameter("longitude")).thenReturn("10.0673");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.FEIL)).toString();
        assertTrue(result.contains(expected));
    }
    
    @Test
    public void hentAvfallsplassarInnanforRekkeviddeGirOk() throws ServletException, IOException {
        when(request.getParameter("latitude")).thenReturn("61.457538");
        when(request.getParameter("longitude")).thenReturn("5.848933");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        when(response.getWriter()).thenReturn(pw);

        servlet.doGet(request, response);
        String result = sw.getBuffer().toString();
        String expected = gson.toJson(new Melding(Meldingstype.OK)).toString();
        assertTrue(result.contains(expected));
    }

}