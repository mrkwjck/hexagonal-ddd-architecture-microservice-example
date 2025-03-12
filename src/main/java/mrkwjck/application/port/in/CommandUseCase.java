package mrkwjck.application.port.in;

interface CommandUseCase<I> {

    void execute(I input);
}
