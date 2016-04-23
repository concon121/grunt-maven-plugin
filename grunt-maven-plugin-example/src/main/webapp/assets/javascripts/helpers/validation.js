function setupValidation() {
  console.log("Settig up validation");

  ko.validation.rules.pattern.message = 'Invalid.';

  ko.validation.init({
    registerExtenders: true,
    messagesOnModified: true,
    insertMessages: true,
    parseInputAttributes: true,
    messageTemplate: null
  }, true);
}
