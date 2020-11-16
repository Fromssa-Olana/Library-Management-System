package com.librarymanagement.application.views.member;
import com.librarymanagement.application.backend.dto.Address;
import com.librarymanagement.application.backend.dto.Member;
import com.librarymanagement.application.backend.service.AddressService;
import com.librarymanagement.application.backend.service.MemberService;
import com.librarymanagement.application.views.main.MainView;
import com.vaadin.flow.component.*;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Route(value = "members", layout = MainView.class)
@PageTitle("Members")
@CssImport("./styles/views/members/members-view.css")
@RouteAlias(value = "", layout = MainView.class)
public class memberView extends Div {

    private Grid<Member> grid = new Grid<>(Member.class);

    // member input fields
    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private TextField phone = new TextField("Phone");
    private DatePicker dateOfBirth = new DatePicker("Date of birth");
    private TextField occupation = new TextField("Occupation");

    //member Address inputs

    private TextField houseNumber = new TextField("House Number");
    private TextField city = new TextField("City");
    private TextField state = new TextField("State");
    private TextField zipCode = new TextField("ZipCode");

    // filter and add member button
    private TextField filterField = new TextField();
    private Button addMemberButton = new Button("Add Member");
    private boolean addMember = false;

    // Form Buttons
    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    //Binders
    private Binder<Member> binder;
    private Binder<Address> addressBinder;

    private Member member = new Member();
    private Address address = new Address();

    private MemberService memberService;
    @Autowired
    private AddressService addressService;

    public memberView(@Autowired MemberService memberService) {
        setId("members-view");
        this.memberService = memberService;
        // Configure Grid

        // when a row is selected or deselected, populate form
        dataBindingFromSelect(memberService);

        // Configure Form
        binder = new Binder<>(Member.class);
        addressBinder = new Binder<>(Address.class);



        // Bind fields. This where you'd define e.g. validation rules
        binder.bindInstanceFields(this);
        addressBinder.bindInstanceFields(this);

        configureGrid();

        configCancelButton();

        configSaveButton();

        configDeleteButton();

        configAddMemberButton();

        configFilterField();

        add(getFilterAndAddMemberLayout(),createGridLayout());
    }

    private HorizontalLayout getFilterAndAddMemberLayout() {
        return new HorizontalLayout(filterField, addMemberButton);
    }


    private void addMemberForm() {
        removeAll();
        add(getFilterAndAddMemberLayout(),
                new HorizontalLayout(createGridLayout(),
                        createEditorLayout()));
    }

    private void removeMemberForm(){
        removeAll();
        add(getFilterAndAddMemberLayout(),
                createGridLayout());
    }

    private void configDeleteButton() {
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        delete.setAutofocus(true);
        delete.addClickListener(click -> {
            memberService.delete(this.member);
            clearForm();
            updateList();
            Notification.show("Member deleted...");
        });
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
        grid.setItems(memberService.findAll(filterField.getValue()));

    }

    private void configAddMemberButton() {
        addMemberButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addMemberButton.addClickShortcut(Key.ENTER);
        addMemberButton.addClickListener(click -> addMemberForm());

    }


    private void configCancelButton() {
        cancel.addClickListener(e -> {
            clearForm();
            updateList();
            removeMemberForm();
        });
    }

    private void configSaveButton() {
        save.addClickListener(e -> {
            try {
                if (this.member == null && this.address == null) {
                    this.member = new Member();
                    this.address = new Address();
                }
                binder.writeBean(this.member);
                addressBinder.writeBean(this.address);
                this.member.setAddress(this.address);
                memberService.save(this.member);



                clearForm();
                updateList();
                Notification.show("Member details stored.");
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the member details.");
            }
        });
    }

    private void dataBindingFromSelect(MemberService memberService) {
        grid.asSingleSelect().addValueChangeListener(event -> {
            addMemberForm();
            if (event.getValue() != null) {
                Optional<Member> personFromBackend = memberService.get(event.getValue().getId());
                // when a row is selected but the backend is no longer available, refresh grid
                if (personFromBackend.isPresent()) {
                    populateForm(personFromBackend.get());
                    populateForm(personFromBackend.get());
                } else {
                    updateList();
                }
            } else {
                clearForm();
            }
        });
    }

    private void configureGrid() {
        grid.setColumns("firstName", "lastName", "email", "phone", "dateOfBirth", "occupation");
        grid.addColumn(member1 -> {
            Address address = member1.getAddress();
            return address == null? "-":address.getCity();
        }).setHeader("City");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
        grid.setItems(memberService.findAll());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);


    }

    private Component createEditorLayout() {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setId("editor-layout");

        Div editorDiv = new Div();
        editorDiv.setId("editor");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        AbstractField[] fields = new AbstractField[]{
                firstName, lastName, email, phone, dateOfBirth, occupation,
                houseNumber,city,state,zipCode
        };
        for (AbstractField field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);
        return editorLayoutDiv;

    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setId("button-layout");
        buttonLayout.setWidthFull();
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel, delete);
        editorLayoutDiv.add(buttonLayout);
    }

    private Component createGridLayout() {
        Div wrapper = new Div();
       // wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        wrapper.add(grid);
       wrapper.setWidthFull();
        wrapper.setHeightFull();
        return wrapper;

    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Member value) {
        this.member = value;
        binder.readBean(this.member);

    }
}
