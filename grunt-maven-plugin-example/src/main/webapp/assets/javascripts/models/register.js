var RegisterModel = {
  forename: "",
  surname: "",
  gender: "",
  email: "",
  phone: ""
};

var RegistrationView = {
  registrationData: ko.observable(null),

  displayForm: ko.observable({}),

  forename: ko.observable("").extend({required: {message: "Please enter your forename"}}),
  surname: ko.observable("").extend({required: {message: "Please enter your surname"}}),
  gender: ko.observable(""),
  email: ko.observable("").extend({required: {message: "Please enter your email address"}}),
  phone: ko.observable("").extend({required: true, pattern: '^[0-9]*$'}),

  register: function() {
    console.log(this.registrationData);
    this.registrationData(null);
  },

  completeRegistration: function() {
    console.log(this.registrationData);
    console.log("Complete Registration");
    if (RegistrationView.errors().length === 0) {
      console.log("is valid");

      RegisterModel.forename = RegistrationView.forename._latestValue;
      RegisterModel.surname = RegistrationView.surname._latestValue;
      RegisterModel.email = RegistrationView.email._latestValue;
      RegisterModel.phone = RegistrationView.phone._latestValue;
      RegisterModel.gender = RegistrationView.gender._latestValue;

      RegistrationView.displayForm(null);
      RegistrationView.registrationData(RegisterModel);
    }
    else {
      RegistrationView.errors.showAllMessages();
    }
  }
};

$("#templates").load("public/templates.html", function() {
  setupValidation();
  RegistrationView.displayForm(true);
  RegistrationView.errors = ko.validation.group(RegistrationView);
  ko.applyBindings(RegistrationView);
});
