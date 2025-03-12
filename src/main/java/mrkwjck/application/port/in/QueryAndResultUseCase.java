package mrkwjck.application.port.in;

interface QueryAndResultUseCase<I, O> {

    O execute(I input);
}
