package com.librarymanagement.application.views.member;

import java.util.Optional;

import com.librarymanagement.application.data.entity.Address;
import com.librarymanagement.application.data.entity.Member;
import com.librarymanagement.application.data.service.MemberService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import com.librarymanagement.application.views.main.MainView;
import com.vaadin.flow.router.RouteAlias;

@Route(value = "members", layout = MainView.class)
@PageTitle("Members")
@CssImport("./styles/views/members/members-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class memberView extends Div {

    private Grid<Member> grid;

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private TextField phone = new TextField("Phone");
    private DatePicker dateOfBirth = new DatePicker("Date of birth");
    private TextField occupation = new TextField("Occupation");

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private Binder<Member> binder;

    private Member member = new Member();
    private Address address = new Address();

    private MemberService memberService;

    public memberView(@Autowired MemberService memberService) {
        setId("members-view");
        this.memberService = memberService;
        // Configure Grid
        configureGrid(memberService);

        // when a row is selected or deselected, populate form
        dataBindingFromSelect(memberService);

        // Configure Form
        binder = new Binder<>(Member.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);

        configCancelButton();

        configSaveButton(memberService);

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);
    }

    private void configCancelButton() {
        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });
    }

    private void configSaveButton(MemberService memberService) {
        save.addClickListener(e -> {
            try {
                if (this.member == null) {
                    this.member = new Member();
                    address.setAddress("1234 Burns Ave ");
                   // member.setAddress(this.address);
                }
                binder.writeBean(this.member);
                memberService.save(this.member);
                clearForm();
                refreshGrid();
                Notification.show("Member details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the member details.");
            }
        });
    }

    private void dataBindingFromSelect(MemberService memberService) {
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<Member> personFromBackend = memberService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if (personFromBackend.isPresent()) {
                    populateForm(personFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });
    }

    private void configureGrid(MemberService memberService) {
        grid = new Grid<>(Member.class);
        grid.setColumns("firstName", "lastName", "email", "phone", "dateOfBirth", "occupation");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setItems(memberService.findAll());
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
        AbstractField[] fields = new AbstractField[] { firstName, lastName, email, phone, dateOfBirth, occupation };
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

    private void populateForm(Member value) {
        this.member = value;
        binder.readBean(this.member);
    }
}
