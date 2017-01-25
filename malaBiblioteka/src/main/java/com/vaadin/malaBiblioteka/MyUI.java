package com.vaadin.malaBiblioteka;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.swing.text.AttributeSet.FontAttribute;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.control.UnosControl;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.model.Knjige;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.Button.ClickEvent;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.vaadin.malaBiblioteka.MyAppWidgetset")
public class MyUI extends UI {
	
	private UnosControl control=UnosControl.getInstance();
	private Grid grid=new Grid();
	
	private TextField search=new TextField();
	 private Button searchButton=new Button(FontAwesome.SEARCH);
	 private BookForm form=new BookForm(this);
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        Label naslov=new Label("Mala biblioteka");
        naslov.setStyleName(ValoTheme.LABEL_HUGE);
        layout.addComponent(naslov);
       
        search.setInputPrompt("Pretraga po nazivu...");
      //  search.addTextChangeListener(e -> {grid.setContainerDataSource(new BeanItemContainer<>(Knjige.class,control.findAll(e.getText())));
      // });
        
       Button clearSearchButton=new Button(FontAwesome.TIMES);
       clearSearchButton.setClickShortcut(KeyCode.ENTER);
       clearSearchButton.addClickListener(e ->{
    	   search.clear();
    	   updateList();
    	   
    	   
       });
       
       CssLayout cssLayout=new CssLayout();
       cssLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
       cssLayout.addComponents(search,searchButton,clearSearchButton);
       
       	Button add=new Button("Add new book");
       	add.addClickListener(e ->{
       		grid.select(null);
       		Knjige knjige=new Knjige();
       		form.setBook(knjige);
       		
       	});
       	
       	HorizontalLayout toolbar=new HorizontalLayout(cssLayout,add);
       	toolbar.setSpacing(true);
        grid.setColumns("isbn","naziv","autor","zanr","cena","opis");
        HorizontalLayout main=new HorizontalLayout(grid,form);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);
        layout.addComponents(toolbar,main);
        searchButton.setClickShortcut(KeyCode.ENTER);
        searchButton.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				String naziv=search.getValue();
				
				if(naziv.equals("")){
                	Notification.show(null,"Please, enter name book!",Notification.Type.WARNING_MESSAGE);
                
                }else{
                	
                	List<Knjige> lista=control.byName(naziv);
                	
                    grid.setContainerDataSource(new BeanItemContainer<>(Knjige.class, lista));
                    
                }
               
			}
		});
        updateList();
       
        
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
        form.setVisible(false);
        grid.addSelectionListener(event -> {
        	if(event.getSelected().isEmpty()){
        		form.setVisible(false);
        	}else{
        		Knjige knjige=(Knjige)event.getSelected().iterator().next();
        		form.setBook(knjige);
        	}
        	
        	
        	
        });
    }

	public void updateList() {
		List<Knjige> lista=control.getAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Knjige.class, lista));
        
	}
	
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
