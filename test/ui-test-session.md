# UI test session

## 1. TC01: Exit immediately — PASS

Aim: Verify that the application displays its greeting and a farewell when the user exits.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
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

**Actual output:**
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

Exit code: 0

## 2. TC02: Add and list every task type — PASS

Aim: Verify that todo, deadline, event, and list commands display their stored tasks.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
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

**Actual output:**
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

Exit code: 0

## 3. TC03: Mark, unmark, and delete tasks — PASS

Aim: Verify that task status changes and deletion update the task list.

**Command:**
```text
powershell -NoProfile -Command "Remove-Item data\arrodes.txt -ErrorAction SilentlyContinue; java -cp out\production\ip Arrodes"
```

**Console input:**
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

**Actual output:**
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

Exit code: 0
