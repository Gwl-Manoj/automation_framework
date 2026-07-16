# Create JMeter Script

## Role
You are an expert performance test engineer specializing in JMeter and load testing.

## Task
Create a comprehensive JMeter test script following best practices and performance testing standards.

## Context
- **Tool**: Apache JMeter
- **Purpose**: Performance and load testing
- **Protocol**: HTTP/HTTPS (can be extended to other protocols)
- **Output**: JMX file that can be executed in JMeter GUI or CLI mode

## Requirements

### Test Information
- **Test Name**: [Provide test name]
- **Test Description**: [Describe what the test validates]
- **Test Type**: [Load Test, Stress Test, Soak Test, Spike Test]
- **Target Application**: [Application name and URL]

### Test Scenario
[Describe the performance test scenario:]
1. User flow to be tested
2. Number of virtual users
3. Test duration
4. Ramp-up period
5. Think time between requests
6. Expected throughput

### Performance Metrics
- **Concurrent Users**: [Number of virtual users]
- **Ramp-up Time**: [Time to reach full load]
- **Test Duration**: [How long to maintain load]
- **Expected Response Time**: [Target response time in ms]
- **Expected Throughput**: [Requests per second]
- **Error Rate Threshold**: [Acceptable error percentage]

## Instructions

### 1. Test Plan Structure
Create a JMeter test plan with:
- Thread Group (virtual users)
- HTTP Request samplers
- Listeners (for results)
- Assertions (for validation)
- Timers (for think time)
- Config Elements (CSV Data Set, HTTP Header Manager, etc.)

### 2. Thread Group Configuration
```xml
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">[number-of-users]</stringProp>
    <stringProp name="ThreadGroup.ramp_time">[ramp-up-time]</stringProp>
    <stringProp name="ThreadGroup.duration">[test-duration]</stringProp>
    <boolProp name="ThreadGroup.scheduler">true</boolProp>
</ThreadGroup>
```

### 3. HTTP Request Configuration
```xml
<HTTPSamplerProxy>
    <stringProp name="HTTPSampler.domain">[domain]</stringProp>
    <stringProp name="HTTPSampler.port">[port]</stringProp>
    <stringProp name="HTTPSampler.path">[/path]</stringProp>
    <stringProp name="HTTPSampler.method">[GET/POST/PUT/DELETE]</stringProp>
    <stringProp name="HTTPSampler.body">[request-body]</stringProp>
</HTTPSamplerProxy>
```

### 4. Test Plan Components

#### Thread Group
- Number of Threads (users)
- Ramp-up Period (seconds)
- Loop Count or Duration
- Scheduler settings

#### HTTP Request Defaults
- Server Name or IP
- Port Number
- Protocol
- Content Encoding
- Path

#### HTTP Header Manager
- Content-Type: application/json
- Authorization: Bearer token
- Accept: application/json
- Custom headers

#### CSV Data Set Config
- File path for test data
- Variable names
- Delimiter
- Recycle on EOF
- Stop thread on EOF

#### Timers
- Constant Timer (fixed delay)
- Uniform Random Timer (variable delay)
- Gaussian Random Timer (normal distribution)

#### Assertions
- Response Assertion (validate response)
- Duration Assertion (response time)
- Size Assertion (response size)

#### Listeners
- Summary Report
- Aggregate Report
- View Results Tree (for debugging)
- Backend Listener (for Grafana/InfluxDB)

## Code Template

### JMeter Test Plan (JMX Structure)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="5.0" jmeter="5.4.1">
    <hashTree>
        <TestPlan>
            <stringProp name="TestPlan.comments">[Test Description]</stringProp>
            <boolProp name="TestPlan.functional_mode">false</boolProp>
            <boolProp name="TestPlan.tearDown_on_shutdown">true</boolProp>
            <boolProp name="TestPlan.serialize_threadgroups">false</boolProp>
            <elementProp name="TestPlan.user_defined_variables">
                <collectionProp name="Arguments.arguments">
                    <elementProp name="base_url" elementType="Argument">
                        <stringProp name="Argument.name">base_url</stringProp>
                        <stringProp name="Argument.value">https://api.example.com</stringProp>
                    </elementProp>
                </collectionProp>
            </elementProp>
        </TestPlan>
        <hashTree>
            <ThreadGroup>
                <stringProp name="ThreadGroup.num_threads">[users]</stringProp>
                <stringProp name="ThreadGroup.ramp_time">[ramp-up]</stringProp>
                <stringProp name="ThreadGroup.duration">[duration]</stringProp>
                <boolProp name="ThreadGroup.scheduler">true</boolProp>
            </ThreadGroup>
            <hashTree>
                <!-- Add HTTP Requests, Timers, Assertions, Listeners here -->
            </hashTree>
        </hashTree>
    </hashTree>
</jmeterTestPlan>
```

### Example: API Load Test
```xml
<?xml version="1.0" encoding="UTF-8"?>
<jmeterTestPlan version="1.2" properties="5.0" jmeter="5.4.1">
    <hashTree>
        <TestPlan>
            <stringProp name="TestPlan.comments">User API Load Test</stringProp>
        </TestPlan>
        <hashTree>
            <ThreadGroup>
                <stringProp name="ThreadGroup.num_threads">100</stringProp>
                <stringProp name="ThreadGroup.ramp_time">60</stringProp>
                <stringProp name="ThreadGroup.duration">300</stringProp>
                <boolProp name="ThreadGroup.scheduler">true</boolProp>
            </ThreadGroup>
            <hashTree>
                <!-- HTTP Request Defaults -->
                <ConfigTestElement>
                    <stringProp name="HTTPSampler.domain">api.example.com</stringProp>
                    <stringProp name="HTTPSampler.port">443</stringProp>
                    <stringProp name="HTTPSampler.protocol">https</stringProp>
                </ConfigTestElement>
                <hashTree/>
                
                <!-- HTTP Header Manager -->
                <HeaderManager>
                    <collectionProp name="HeaderManager.headers">
                        <elementProp name="Content-Type" elementType="Header">
                            <stringProp name="Header.name">Content-Type</stringProp>
                            <stringProp name="Header.value">application/json</stringProp>
                        </elementProp>
                        <elementProp name="Authorization" elementType="Header">
                            <stringProp name="Header.name">Authorization</stringProp>
                            <stringProp name="Header.value">Bearer ${auth_token}</stringProp>
                        </elementProp>
                    </collectionProp>
                </HeaderManager>
                <hashTree/>
                
                <!-- GET /api/users -->
                <HTTPSamplerProxy>
                    <stringProp name="HTTPSampler.path">/api/users</stringProp>
                    <stringProp name="HTTPSampler.method">GET</stringProp>
                </HTTPSamplerProxy>
                <hashTree>
                    <ResponseAssertion>
                        <stringProp name="Assertion.test_field">Response Code</stringProp>
                        <stringProp name="Assertion.test_type">8</stringProp>
                        <stringProp name="Assertion.test_scope">main</stringProp>
                    </ResponseAssertion>
                </hashTree>
                
                <!-- Timers -->
                <ConstantTimer>
                    <stringProp name="ConstantTimer.delay">1000</stringProp>
                </ConstantTimer>
                <hashTree/>
            </hashTree>
        </hashTree>
    </hashTree>
</jmeterTestPlan>
```

## Best Practices

### DO's
✓ Use realistic user scenarios
✓ Include think time (timers)
✓ Use CSV data for parameterization
✓ Set appropriate ramp-up time
✓ Monitor server resources
✓ Use assertions to validate responses
✓ Run tests in non-GUI mode for production
✓ Save results for analysis

### DON'Ts
✗ Don't run tests from GUI (use CLI)
✗ Don't use too many listeners (impacts performance)
✗ Don't ignore response time thresholds
✗ Don't test without proper environment setup
✗ Don't use hardcoded values (use variables)
✗ Don't forget to clean up test data

## Common Test Types

### 1. Load Test
```xml
<!-- Test normal expected load -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">100</stringProp>
    <stringProp name="ThreadGroup.ramp_time">60</stringProp>
    <stringProp name="ThreadGroup.duration">600</stringProp>
</ThreadGroup>
```

### 2. Stress Test
```xml
<!-- Test beyond normal load to find breaking point -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">500</stringProp>
    <stringProp name="ThreadGroup.ramp_time">120</stringProp>
    <stringProp name="ThreadGroup.duration">600</stringProp>
</ThreadGroup>
```

### 3. Soak Test
```xml
<!-- Test over extended period for memory leaks -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">50</stringProp>
    <stringProp name="ThreadGroup.ramp_time">30</stringProp>
    <stringProp name="ThreadGroup.duration">86400</stringProp> <!-- 24 hours -->
</ThreadGroup>
```

### 4. Spike Test
```xml
<!-- Test sudden spike in traffic -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">1000</stringProp>
    <stringProp name="ThreadGroup.ramp_time">10</stringProp>
    <stringProp name="ThreadGroup.duration">60</stringProp>
</ThreadGroup>
```

## Test Elements

### Thread Group Elements
```xml
<!-- Number of virtual users -->
<stringProp name="ThreadGroup.num_threads">100</stringProp>

<!-- Ramp-up time (seconds to start all users) -->
<stringProp name="ThreadGroup.ramp_time">60</stringProp>

<!-- Test duration (seconds) -->
<stringProp name="ThreadGroup.duration">300</stringProp>

<!-- Loop count (use with duration) -->
<stringProp name="LoopController.loops">-1</stringProp>
```

### HTTP Request Elements
```xml
<!-- Server configuration -->
<stringProp name="HTTPSampler.domain">api.example.com</stringProp>
<stringProp name="HTTPSampler.port">443</stringProp>
<stringProp name="HTTPSampler.protocol">https</stringProp>
<stringProp name="HTTPSampler.path">/api/users</stringProp>
<stringProp name="HTTPSampler.method">GET</stringProp>

<!-- Request body (for POST/PUT) -->
<stringProp name="HTTPSampler.body">{"name":"John"}</stringProp>
```

### Timer Elements
```xml
<!-- Constant Timer (fixed delay) -->
<ConstantTimer>
    <stringProp name="ConstantTimer.delay">1000</stringProp>
</ConstantTimer>

<!-- Uniform Random Timer (random delay) -->
<UniformRandomTimer>
    <stringProp name="UniformRandomTimer.delay">1000</stringProp>
    <stringProp name="UniformRandomTimer.range">500</stringProp>
</UniformRandomTimer>
```

### Assertion Elements
```xml
<!-- Response Assertion -->
<ResponseAssertion>
    <stringProp name="Assertion.test_field">Response Code</stringProp>
    <stringProp name="Assertion.test_type">8</stringProp>
    <stringProp name="Assertion.test_scope">main</stringProp>
</ResponseAssertion>

<!-- Duration Assertion -->
<DurationAssertion>
    <stringProp name="DurationAssertion.duration">2000</stringProp>
</DurationAssertion>
```

### Listener Elements
```xml
<!-- Summary Report -->
<SummaryReport/>

<!-- Aggregate Report -->
<AggregateReport/>

<!-- Backend Listener (InfluxDB) -->
<BackendListener>
    <stringProp name="backend">influxdb</stringProp>
</BackendListener>
```

## Command Line Execution

### Basic Command
```bash
jmeter -n -t test-plan.jmx -l results.jtl -e -o report
```

### Parameters
- `-n`: Non-GUI mode
- `-t`: Test plan file
- `-l`: Results log file
- `-e`: Generate report
- `-o`: Output directory for report
- `-J`: Set JMeter properties

### Example
```bash
jmeter -n -t user-api-load-test.jmx -l results/results.jtl -e -o results/report
```

## Performance Metrics to Monitor

### Key Metrics
1. **Response Time**: Average, min, max, percentiles (90th, 95th, 99th)
2. **Throughput**: Requests per second
3. **Error Rate**: Percentage of failed requests
4. **Active Threads**: Number of concurrent users
5. **Bytes Throughput**: Data sent/received per second

### Acceptance Criteria
```properties
# Example acceptance criteria
response.time.avg<500ms
response.time.95thpercentile<1000ms
throughput>100req/s
error.rate<1%
```

## Test Data Management

### CSV Data Set Config
```xml
<CSVDataSet>
    <stringProp name="filename">test-data/users.csv</stringProp>
    <stringProp name="fileEncoding">UTF-8</stringProp>
    <stringProp name="variableNames">username,password,email</stringProp>
    <stringProp name="delimiter">,</stringProp>
    <boolProp name="quotedData">false</boolProp>
    <boolProp name="recycle">true</boolProp>
    <boolProp name="stopThread">false</boolProp>
    <boolProp name="shareMode">shareMode.all</boolProp>
</CSVDataSet>
```

### Test Data File Format
```csv
username,password,email
user1,pass1,user1@example.com
user2,pass2,user2@example.com
user3,pass3,user3@example.com
```

## Reporting

### HTML Report
- Automatically generated with `-e -o` flags
- Includes graphs and charts
- Shows response time trends
- Displays throughput and error rates

### Key Report Sections
1. **Dashboard**: Overview of test execution
2. **Charts**: Response time, throughput, active threads
3. **Tables**: Detailed statistics
4. **Errors**: Failed requests analysis

## Integration with CI/CD

### Jenkins Pipeline
```groovy
stage('Performance Test') {
    steps {
        sh 'jmeter -n -t tests/load-test.jmx -l results.jtl'
        publishHTML([
            reportDir: 'report',
            reportFiles: 'index.html',
            reportName: 'Performance Report'
        ])
    }
}
```

## Best Practices

### Test Design
✓ Model real user behavior
✓ Include realistic think times
✓ Use production-like data
✓ Test from multiple locations (if needed)
✓ Monitor server metrics during test

### Script Development
✓ Use meaningful names for elements
✓ Add comments for complex scenarios
✓ Parameterize all variable data
✓ Use assertions to validate responses
✓ Test script in GUI mode first

### Execution
✓ Run in non-GUI mode
✓ Use appropriate load levels
✓ Monitor system resources
✓ Analyze results thoroughly
✓ Compare with baseline metrics

## Troubleshooting

### Common Issues
1. **Out of Memory**: Increase heap size in jmeter.bat/sh
2. **Too Many Listeners**: Remove unnecessary listeners
3. **Connection Errors**: Check network and server capacity
4. **High Response Times**: Check server performance
5. **Errors**: Validate request format and authentication

### JMeter Properties
```properties
# jmeter.properties
# Increase heap size
-Xms2g -Xmx4g

# Disable DNS cache
httpclient4.retrycount=0

# Connection timeout
httpclient4.timeout=30000
```

## Additional Notes
- Always test script in GUI mode first
- Use non-GUI mode for actual testing
- Monitor server resources during test
- Analyze results before making conclusions
- Run tests in isolated environment
- Document test scenarios and results