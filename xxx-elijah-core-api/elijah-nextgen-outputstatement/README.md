Elijah OutputStatement
=======================

### Purpose

- Provide strings that have an explanation
- Provide strings that can be updated based on inputs
  - (not impl)

### (2)

- Compound
  - single beginning, ending
  - abstract/unconstrained middle
- Dotted
  - a list separated by a (string) separator
- Enclosed
  - (not impl/specc'ed)
  - Likely a C block ('{', ..., '}')
- Sequence
  - string beginning, ending
  - list of statement list/middle
  - optional Naming
- Single
  - A simple string
- Synthetic
  - Naming, Symbol/String, Rule
  - ??
  

- Naming
  - a name (?) ...
    - should be unique within a generated file/tree
      - we don't do trees (yet??)
  - ... for things that require/elect it
    - ie Sequence, but should be all
  - string first
  - optional (read nullable) string second part


### Things to think about

1. What is the difference between EX_Rule and EX_Explanation?
2. Attach to buffers so you can have your Monaco/CodeMirror nonsense
