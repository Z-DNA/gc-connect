# GC-Connect
## What it is?
Gc connect is the response for the problem of mutual sharing contact details with the group of loose fellows even when we are not sure who they are in person.

Gathering a lot of data in one play can be challenging as one has little to no control over security of those data:
- Either they can be shared in simple document like `docs` or `sheets` based on `everyone who has a link` type of security:
    - It generates possibility of data damage or uncontrolled leakage
- Or they can be hidden behind `access granted upon request` rule:
    - It generates problems with `authentication` those requests and still doesn't solve malicious/accidental mutual data damage risk.

Proposed solution is based on mutual trust and connections. The **only** (excluding *first* user) possible way into the app is invitation (account creation) from someone already in the app.

## Where did the idea come from?
When we are pursuing our hobbies (especially those based on usernames) we often end up knowing bunch of people, but mostly we don't know their real life name but their in-game/hobby nickname. My hobby is based on "treasure hunt" where bunch of people are hiding containers/boxes for others to find. A lot of new people are arriving and often during the search we urgently need contact to the container's owner. But despite knowing him quite well - we don't have his mobile. So the *"do you have number to X"*-odyssey begins...

## How does it work?
- The only way to create an account is through an invitation from someone with an `active` account
- This person can create on their behalf account for new person providing their *in-game* `username` and their `phone number`. Both values are heavily validated
- `Username` is checked using external api to check that there *exists* user within external service with that username. Authentication is not ~~possible~~ technically reasonable
- `Phone number` is checked using Google's `PhoneNumberUtil`. For now **only** Polish mobile numbers are allowed. There is no authentication of that number
- User can choose between 3 `Privacy` settings:
    - `PUBLIC` - contact data available in `public` tab in app
    - `PRIVATE`- *default* option. The contact data will be shared within created `network` of `connected` friends. We can assume `connection` to be **equivalence relation**. That means that contact data are propagated within **equivalence class**. $A \sim B \sim C \implies A \sim C$
    - `HIDDEN` - contact data works **only** in the `inverse search`: *"ok... so... who called me?"*. It translates `phone number` into the `username`

## Architecture and Technical aspects
The application is created in `Java 23` with the `Spring 6` framework. The architecture is based on **4 layered clean architecture** and **Modular Monolith** pattern also inspired on *screaming architecture*, *onion* and *vertical slice*.

It was designed with **DDD** in mind, and it's mostly **event-driven** to emphasize its *modular* nature. The events are based on *choreography-based saga* pattern (not fully implemented yet).

The *presentation layer* is created with *thymeleaf* with the purpose of creating simple interface. Application security is based on `Auth0` tokens.

The *repository/persistence layer* is not created yet. It'll be implementing `MongoDB` and will be implemented as the next feature.

Application right now have only one "module" nearly completed which covers crucial integrations. The other modules and context will be expanded much quicker as they will mostly be based on database `CRUD` operations.

## FAQ
#### What `VGN` stands for?
It's abbreviation of `ValidatorsGeneratorsNormalizers`. It's `abstract factory` utilizing *OCP* allowing to simple usage across app (as valid data are critical domain logic)

#### Why such a simple app has such (over)complicated architecture?
>*If you can't explain it to a six-year-old, then you don't understand it yourself*
>~Einstein (allegedly)

On such a simple app many architectural patterns and principles are easily visible as they are not obfuscated with heavy logic. That makes them lucid as if there were *templates*. 