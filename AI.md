### Tools and usage

- Cursor free Auto model: used for most standard tasks (minor refactors, routine docs, small test scaffolds).
- GPT-5 model: used for optimizing design and nuanced testing (e.g. edge-case identification in `Storage.java`, invariants and parsing in `Parser.java`, minimal-diff refactors for `ListCommand`/`Ui`).

### Observations (What Worked Better)

- Anchor prompts to exact files/classes/tests and request minimal diffs with acceptance criteria (e.g., “In `Parser.parse`, allow `yyyy/MM/dd` in the `deadline` branch only; update `ParserDeadlineTest`; don’t touch other commands”).
- Ground the assistant with precise targets: mention `Storage.save`, `Storage` serialization format (pipe-delimited T/D/E), `TaskList.asList`, and `Ui.showWelcome` when relevant so changes land in the right place on the first try.
- Pair every change with tests: ask to add/adjust `src/test/java/dukii/*Test.java` first, then implement; run `gradlew test` and require green tests before accepting edits.
- Preserve invariants and interfaces: keep storage schema stable, keep `TaskList` API unchanged, maintain existing error-message tone in `DukiiException`/`Parser` and greeting in `Ui`.
- Constrain scope and failure modes explicitly: state files not to edit, forbid unrelated refactors, and say “reject if it changes `build.gradle` or public method signatures.”
- Prefer mechanical work for AI: Javadoc, small unit tests, trivial refactors (e.g., extracting duplicate message strings), and formatting; avoid speculative redesigns.
- Decompose multi-step tasks into ordered steps: parse → tests → persist → UI message; have the assistant complete steps sequentially and verify after each.
- Ask for reasoning, demand concise output: “think through edge cases (empty desc, bad dates, off-by-one indices), then output only the final diff touching the minimal lines.”
- Set version-control discipline: stage only changed files, no commits until review; draft commit messages that are short and direct (e.g., “Support slash dates in deadline parser”).
- Safety guardrails: no new dependencies, jar stays `dukii`, no I/O outside `data/dukii.txt`, defaults remain `yyyy-MM-dd` unless tests expand coverage.
- Be explicit about acceptance criteria: name the tests expected to pass (e.g., `ParserDeadlineTest`, `ParserEventTest`, `ListCommandTest`) and the exact behavior change.
