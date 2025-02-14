# Contributing to Spring Security JWT Template

First off, thank you for considering contributing to Spring Security JWT Template! It's people like you that make the open source community such a great place to learn, inspire, and create.

## Code of Conduct

This project and everyone participating in it is governed by our Code of Conduct. By participating, you are expected to uphold this code. Please report unacceptable behavior to spacecodee@gmail.com.

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check the issue list as you might find out that you don't need to create one. When you are creating a bug report, please include as many details as possible:

* Use a clear and descriptive title
* Describe the exact steps which reproduce the problem
* Provide specific examples to demonstrate the steps
* Describe the behavior you observed after following the steps
* Explain which behavior you expected to see instead and why
* Include stacktraces, log files if applicable
* Include your environment details (OS, JDK version, etc.)

### Suggesting Enhancements

Enhancement suggestions are tracked as GitHub issues. Create an issue and provide the following information:

* Use a clear and descriptive title
* Provide a step-by-step description of the suggested enhancement
* Provide specific examples to demonstrate the steps
* Describe the current behavior and explain which behavior you expected to see instead

### Pull Requests

1. Fork the repository and create your branch from `main`
2. If you've added code that should be tested, add tests
3. Ensure all tests pass
4. Make sure your code follows the existing code style
5. Write a good commit message

## Development Environment Setup

1. Fork and clone the repository
2. Install Docker and Docker Compose
3. Copy `.env.example` to `.env` in the .devcontainer folder and adjust values as needed
4. Copy `application-local.properties.example` to `application-local.properties` in resources folder
5. Open the project in VS Code with Dev Containers extension or your preferred IDE with dev containers support
6. The dev container will automatically set up:
   - JDK 21
   - Gradle
   - PostgreSQL
   - Required dependencies
7. Run `./gradlew bootRun` to start the application

## Coding Conventions

* Follow the standard Java code conventions
* Use Spring Framework best practices
* Write JavaDoc for public methods
* Keep methods small and focused
* Write meaningful commit messages following [conventional commits](https://www.conventionalcommits.org/)

## Testing

* Write unit tests for new features
* Ensure all tests pass before submitting a PR
* Aim for high test coverage
* Include integration tests where necessary

## Pull Request Process

1. Update the README.md with details of changes if applicable
2. Update the documentation with details of any new features
3. The PR will be merged once you have the sign-off of at least one maintainer

## Questions?

Don't hesitate to reach out if you have questions. Create an issue or contact the maintainers directly.

Thank you for contributing to Spring Security JWT Template!
