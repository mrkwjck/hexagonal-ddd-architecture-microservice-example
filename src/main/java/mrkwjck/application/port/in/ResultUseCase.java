package mrkwjck.application.port.in;

interface ResultUseCase<IN, OUT> {

    OUT execute(IN input);
}
