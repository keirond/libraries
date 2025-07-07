### Java  Class Naming Convention

- `Reactive` (prefix), `Rx`
- `NoOperation` (prefix), `NoOps`
- `Standard` (prefix), `Std`


- `Controller` (postfix, controller), `Ctrl`
- `Resource` (postfix, controller), `Res`
- `Repository` (postfix, repository), `Repo`
- `Service` (postfix, service), `Svc`
- `Configuration` (postfix, configuration), `Conf`


- `Specification` (postfix), `Spec`
- `Executor` (postfix), `Exec`
- `Handler` (postfix), `Hdlr`
- `Client` (postfix), `Cli`
- `Manager` (postfix), `Mngr`
- `Utilities` (postfix), `Utils`


- `Factory` (postfix)
- `Adapter` (postfix)
- `Provider` (postfix)
- `Job` (postfix)
- `Task` (postfix)


- `Entity` (postfix, indicates DB model), (optional)
- `Model` (postfix, indicates API model), (optional)

---

### Protobuf(Protocol Buffer) Format Convention

#### Backward and Forward Compatibility

Safe changes:

- Add new fields to messages.
- Add deprecated notes for fields.
- Add new RPC methods.
- Change default values.

Breaking changes:

- Remove fields.
- Reuse or change existing field numbers.
- Rename fields (wire format doesn’t care, but clients may rely on generated field names).
- Change request or response types for an existing RPC.
- Change streaming type (unary → streaming) for an existing RPC.

#### Version Format

`{MAJOR}.{MINOR}.{PATCH}`

- Bump `{PATCH}` by 1 if having a change related to request or response fields,
  field additions, field deprecations, default value changed, or comments.
- Bump `{MINOR}` by 1 if having a change related to add new rpc requests.
- Bump `{MIJOR}` by 1 if releasing a new proto file to replace/refactor current proto file.

---