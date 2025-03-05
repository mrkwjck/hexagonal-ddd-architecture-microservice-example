package mrkwjck.application.port.in;

interface ResultUseCase<I, O> {

    O execute(I input);
}
