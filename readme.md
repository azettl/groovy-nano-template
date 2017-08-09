# groovy-nano-template - Template Engine

The groovy-nano-template class replaces placeholders in a string with values from an array.

## Installation

```cmd
```

## Usage

```groovy
Template demo = new Template()
demo.setTemplate('Hello {demooo} {welt}')
demo.setData([demooo:1, welt:'world'])

println(demo.render())
```