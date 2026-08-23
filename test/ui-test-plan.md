# UI test plan

Use one level-two section per test case. The `test-ui` skill executes the command in each case from the project root and compares the complete console output with the expected-output block.

## TC01: Exit immediately

**Aim:** Verify that the application displays its greeting and a farewell when the user exits.

**Command:**
```powershell
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
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

## TC04: Recover from a corrupted data file

**Aim:** Verify that a corrupted data file is reported and the chatbot continues with an empty task list.

**Command:**
```powershell
powershell -NoProfile -Command "Set-Content data\arrodes.txt 'X | 0 | corrupt'; java -cp out\production\ip Arrodes"
```

**Input:**
```text
list
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
Arrodes could not load your requests.
____________________________________________________________
Arrodes recalls your requests:
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________
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

## TC02: Add and list every task type

**Aim:** Verify that todo, deadline, event, and list commands display their stored tasks.

**Command:**
```powershell
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Input:**
```text
todo buy milk
deadline submit report /by tomorrow
event team meeting /from 2pm /to 3pm
list
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
Inscribing request: 
   [T][ ] buy milk
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [D][ ] submit report (by: tomorrow)
2 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [E][ ] team meeting (from: 2pm to 3pm)
3 tasks are being tracked
____________________________________________________________
____________________________________________________________
Arrodes recalls your requests:
1.[T][ ] buy milk
2.[D][ ] submit report (by: tomorrow)
3.[E][ ] team meeting (from: 2pm to 3pm)
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________
```

## TC03: Mark, unmark, and delete tasks

**Aim:** Verify that task status changes and deletion update the task list.

**Command:**
```powershell
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Input:**
```text
todo buy milk
event team meeting /from 2pm /to 3pm
mark 1
unmark 1
delete 2
list
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
Inscribing request: 
   [T][ ] buy milk
1 tasks are being tracked
____________________________________________________________
____________________________________________________________
Inscribing request: 
   [E][ ] team meeting (from: 2pm to 3pm)
2 tasks are being tracked
____________________________________________________________
____________________________________________________________
A worthy task! Arrodes has marked it as done:
  [T][X] buy milk
____________________________________________________________
____________________________________________________________
As you decree, Arrodes has marked this task as not done yet:
  [T][ ] buy milk
____________________________________________________________
____________________________________________________________
Erasing records of the task:

[E][ ] team meeting (from: 2pm to 3pm)

1 tasks remaining are being tracked
____________________________________________________________
____________________________________________________________
Arrodes recalls your requests:
1.[T][ ] buy milk
____________________________________________________________
____________________________________________________________
I shall await your next request...
____________________________________________________________
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
