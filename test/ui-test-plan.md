# UI test plan

Use one level-two section per test case. The `test-ui` skill executes the command in each case from the project root and compares the complete console output with the expected-output block.

## TC01: Exit immediately

**Aim:** Verify that the application displays its greeting and a farewell when the user exits.

**Command:**
```powershell
java -cp out\production\ip Arrodes
```

**Input:**
```text
bye
```

**Expected output:**
```text
____________________________________________________________
    _                       _
   / \   _ __ _ __ ___   __| | ___  ___
  / _ \ | '__| '__/ _ \ / _` |/ _ \/ __|
 / ___ \| |  | | | (_) | (_| |  __/\__ \
/_/   \_\_|  |_|  \___/ \__,_|\___||___/
Eyes that watch All living Beings
The Stigmata from the Primordial Land
The Great Arrodes is before you!
State your request!
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________
```
