package com.librarymanagement.application.views.book;

import com.librarymanagement.application.backend.dto.Book;
import com.librarymanagement.application.backend.service.BookService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.librarymanagement.application.views.main.MainView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route(value = "book", layout = MainView.class)
@PageTitle("Book Catalog")
@CssImport("./styles/views/book/book-view.css")
public class BookView extends Div {


    private Grid<Book> grid;
    // Text fields
    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");

    // filter and add book button
    private TextField filterField = new TextField();
    private Button addMemberButton = new Button("Add Book");


    // Buttons
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");


    private Binder<Book> binder;

    private Book book = new Book();
    private BookService bookService;


    public BookView(@Autowired BookService bookService) {
        setId("book-view");
        this.bookService = bookService;

        configureGrid(bookService);
        // when a row is selected or deselected, populate form
        dataBindingFromSelect(bookService);

        // Configure Form
        binder = new Binder<>(Book.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        configCancelButton();

        configSaveButton(bookService);

        configAddMemberButton();

        configFilterField();


        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(new HorizontalLayout(filterField,addMemberButton), splitLayout);
    }



    /**
     * It configures the filter text field behavior
     */
    private void configFilterField() {
        filterField.setPlaceholder("Filter by name...");
        filterField.setClearButtonVisible(true);
        filterField.setValueChangeMode(ValueChangeMode.LAZY);
        filterField.addValueChangeListener(event -> updateList());
    }

    /**
     * It will update the grid according to the filter need
     */
    private void updateList() {
        grid.setItems(bookService.findAll(filterField.getValue()));

    }

    private void configAddMemberButton() {
        addMemberButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addMemberButton.addClickShortcut(Key.ENTER);

        //TODO implement this
        // addMemberButton.addClickListener();


    }


    private void configCancelButton() {
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
    }

    private void configSaveButton(BookService bookService) {
        save.addClickListener(e -> {
            try {
                if (this.book == null) {
                    this.book = new Book();
                }
                binder.writeBean(this.book);
                bookService.save(this.book);
                clearForm();
                refreshGrid();
                Notification.show("Member details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the member details.");
            }
        });
    }

    private void dataBindingFromSelect(BookService bookService) {
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Book> bookFromBackend = bookService.get(event.getValue().getId());
                // when a row is selected but the backend is no longer available, refresh grid
                if (bookFromBackend.isPresent()) {
                    populateForm(bookFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });
    }

    private void configureGrid(BookService bookService) {
        grid = new Grid<>(Book.class);
        grid.setColumns("title", "author");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setItems( bookService.findAll());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        AbstractField[] fields = new AbstractField[] { title, author};
        for (AbstractField field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(grid);
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Book value) {
        this.book = value;
        binder.readBean(this.book);
    }

}
