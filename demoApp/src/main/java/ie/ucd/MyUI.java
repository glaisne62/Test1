package ie.ucd;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        String connectionString = "jdbc:sqlserver://collegeglash.database.windows.net:1433;database=glashVaadinApp;user=gmckevit@collegeglash;password=T1tGDieK1982@;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
          
        Connection connection = null;
        final VerticalLayout layout = new VerticalLayout();

        
        //Find the application directory
        String basepath = VaadinService.getCurrent()
                            .getBaseDirectory().getAbsolutePath();

        //Image as a file resource
        FileResource resource = new FileResource(new File(basepath + "/WEB-INF/images/gmac.jpg"));

        // Show the image in the application
        Image image = new Image("Image from file", resource);
        image.setHeight(400, Unit.PIXELS);
        

        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        // Let the user view the file in browser or download it
        Link link = new Link("Link to the image file", resource);
        String result = RecogniseFace.recogniseFaces(null);
        //Label theResult = new Label(result);
        TextField theResult = new TextField(RecogniseFace.recogniseFaces(null));
        
        
		try
        {
            connection = DriverManager.getConnection(connectionString);
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM MOCK_DATA");
            Grid<Mockdata> MOCK_DATA = new Grid<>("MOCK_DATA");
            ArrayList<Mockdata> myData = new ArrayList<>();
            while(rs.next())
            {
            myData.add(new Mockdata(rs.getString("first_name"), rs.getString("last_name")));
            }
            MOCK_DATA.setItems(myData);
            MOCK_DATA.addColumn(Mockdata::getFirst_name).setCaption("First");
            MOCK_DATA.addColumn(Mockdata::getLast_name).setCaption("Second");
            
            //setContent(layout);
            layout.addComponents(image, link, MOCK_DATA, theResult);
            //layout.addComponent(image);
            setContent(layout);
        }
        catch(Exception e)
        {
            layout.addComponent(new Label(e.getMessage()));
            
        }
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
