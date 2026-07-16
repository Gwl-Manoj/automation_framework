# Create Performance Scenario

## Role
You are an expert performance test engineer specializing in performance testing strategies and scenario design.

## Task
Create comprehensive performance test scenarios following best practices and industry standards.

## Context
- **Tool**: JMeter, Gatling, or other performance testing tools
- **Purpose**: Validate application performance under various load conditions
- **Scope**: Load testing, stress testing, soak testing, spike testing
- **Metrics**: Response time, throughput, error rate, resource utilization

## Requirements

### Performance Test Information
- **Test Name**: [Provide test name]
- **Test Type**: [Load Test, Stress Test, Soak Test, Spike Test, Endurance Test]
- **Application**: [Application name and URL]
- **Business Scenario**: [User flow to be tested]

### Test Scenario Details
[Describe the performance test scenario:]
1. User journey/workflow
2. Number of concurrent users
3. Test duration
4. Ramp-up and ramp-down periods
5. Think time between actions
6. Expected performance metrics

### Performance Metrics
- **Response Time**: [Target average, 95th percentile, 99th percentile]
- **Throughput**: [Expected requests per second]
- **Concurrent Users**: [Number of virtual users]
- **Error Rate**: [Acceptable error percentage]
- **Resource Utilization**: [CPU, Memory, Database, etc.]

## Instructions

### 1. Performance Test Types

#### Load Test
**Purpose**: Test application under expected normal load

**Scenario**:
- Simulate normal user traffic
- Test with expected concurrent users
- Validate performance meets SLA
- Identify bottlenecks under normal conditions

**Example**:
```
Users: 100 concurrent users
Duration: 30 minutes
Ramp-up: 5 minutes
Think time: 2-3 seconds between requests
Expected response time: < 2 seconds
Expected throughput: 50 requests/second
```

#### Stress Test
**Purpose**: Find breaking point of application

**Scenario**:
- Gradually increase load beyond normal
- Identify maximum capacity
- Test application recovery
- Determine failure thresholds

**Example**:
```
Users: Start with 100, increase to 500
Duration: 20 minutes
Ramp-up: 10 minutes (gradual increase)
Think time: 1-2 seconds
Expected: System should handle 200+ users
Breaking point: Identify at what point failures occur
```

#### Soak Test (Endurance Test)
**Purpose**: Test application stability over extended period

**Scenario**:
- Run with moderate load for extended time
- Detect memory leaks
- Test resource utilization over time
- Validate system stability

**Example**:
```
Users: 50 concurrent users
Duration: 8-24 hours
Ramp-up: 10 minutes
Think time: 3-5 seconds
Monitor: Memory usage, connection pools, database connections
Expected: No degradation over time
```

#### Spike Test
**Purpose**: Test application behavior under sudden traffic spikes

**Scenario**:
- Simulate sudden traffic surge
- Test auto-scaling capabilities
- Validate system recovery
- Test rate limiting

**Example**:
```
Normal load: 50 users
Spike: Jump to 500 users
Spike duration: 5 minutes
Recovery: Back to 50 users
Expected: System handles spike gracefully
Recovery time: < 2 minutes after spike
```

#### Breakpoint Test
**Purpose**: Determine maximum capacity

**Scenario**:
- Continuously increase load
- Find exact breaking point
- Test failure behavior
- Document limits

**Example**:
```
Start: 100 users
Increment: 50 users every 5 minutes
Continue until: 95% error rate or 10 second response time
Document: Breaking point and failure mode
```

### 2. Scenario Design

#### User Journey Mapping
```yaml
Scenario: E-commerce User Journey
Steps:
  1. Browse products (30% of users)
     - GET /api/products
     - Think time: 2-3 seconds
  
  2. Search products (20% of users)
     - GET /api/products?search={keyword}
     - Think time: 1-2 seconds
  
  3. View product details (40% of users)
     - GET /api/products/{id}
     - Think time: 3-5 seconds
  
  4. Add to cart (15% of users)
     - POST /api/cart/items
     - Think time: 1 second
  
  5. Checkout (5% of users)
     - POST /api/orders
     - Think time: 2-3 seconds
```

#### Think Time Distribution
```java
// Realistic think times
Constant Timer: 2000ms (minimum wait)
Uniform Random Timer: 3000ms ± 1000ms (average 3 seconds)
Gaussian Random Timer: 3000ms ± 500ms (normal distribution)
```

#### User Distribution
```java
// Percentage of users performing each action
browseProducts: 30%
searchProducts: 20%
viewDetails: 40%
addToCart: 15%
checkout: 5%

// Note: Percentages add to > 100% because users perform multiple actions
```

### 3. Test Data Strategy

#### Static Test Data
```java
// Pre-defined test data
- Product IDs: 1-1000
- User IDs: 1-100
- Search terms: laptop, phone, tablet, etc.
- Valid/invalid payment methods
```

#### Dynamic Test Data
```java
// Generated at runtime
- Random user sessions
- Unique search queries
- Timestamps for orders
- Random product selections
```

#### Data Volume
```java
// Test with realistic data volumes
- Products: 10,000
- Users: 1,000
- Orders: 100,000
- Categories: 50
```

### 4. Performance Metrics

#### Key Performance Indicators (KPIs)

##### Response Time
```yaml
Average: < 500ms
95th Percentile: < 1000ms
99th Percentile: < 2000ms
Maximum: < 5000ms
```

##### Throughput
```yaml
Requests per second: > 100
Transactions per second: > 50
Data transfer: < 10 MB/s
```

##### Error Rate
```yaml
Total errors: < 1%
HTTP 5xx errors: < 0.1%
Timeouts: < 0.5%
```

##### Concurrent Users
```yaml
Active users: 100-500
Sessions: 100-500
Connections: 200-1000
```

#### Resource Utilization
```yaml
CPU Usage: < 70%
Memory Usage: < 80%
Disk I/O: < 80%
Network: < 60%
Database Connections: < 80% of pool
```

### 5. Scenario Templates

#### Template 1: E-commerce Website Load Test
```yaml
Test Name: E-commerce Load Test
Test Type: Load Test
Duration: 30 minutes
Users: 200 concurrent

User Journey:
  1. Homepage (100%)
     - GET /
     - Think time: 2s
  
  2. Browse Products (80%)
     - GET /products
     - Think time: 3s
  
  3. Search (40%)
     - GET /products?search={keyword}
     - Think time: 2s
  
  4. View Product (60%)
     - GET /products/{id}
     - Think time: 5s
  
  5. Add to Cart (20%)
     - POST /cart
     - Think time: 1s
  
  6. Checkout (5%)
     - POST /orders
     - Think time: 3s

Acceptance Criteria:
  - Average response time: < 1s
  - 95th percentile: < 2s
  - Error rate: < 1%
  - Throughput: > 50 req/s
```

#### Template 2: API Performance Test
```yaml
Test Name: User API Performance Test
Test Type: Load Test
Duration: 15 minutes
Users: 500 concurrent

Endpoints:
  1. GET /api/users (40%)
     - Response time: < 200ms
  
  2. GET /api/users/{id} (30%)
     - Response time: < 100ms
  
  3. POST /api/users (20%)
     - Response time: < 300ms
  
  4. PUT /api/users/{id} (10%)
     - Response time: < 250ms

Acceptance Criteria:
  - Average response time: < 200ms
  - 95th percentile: < 500ms
  - Error rate: < 0.5%
  - Throughput: > 100 req/s
```

#### Template 3: Database Performance Test
```yaml
Test Name: Database Query Performance
Test Type: Load Test
Duration: 20 minutes
Users: 100 concurrent

Queries:
  1. SELECT queries (60%)
     - Simple queries: < 50ms
     - Complex joins: < 200ms
  
  2. INSERT queries (25%)
     - Single insert: < 100ms
     - Batch insert: < 500ms
  
  3. UPDATE queries (10%)
     - Single update: < 100ms
  
  4. DELETE queries (5%)
     - Single delete: < 100ms

Acceptance Criteria:
  - Average query time: < 150ms
  - 95th percentile: < 300ms
  - Deadlocks: 0
  - Connection pool usage: < 80%
```

#### Template 4: Soak Test
```yaml
Test Name: Application Soak Test
Test Type: Soak Test
Duration: 24 hours
Users: 50 concurrent

User Journey:
  - Login (once per session)
  - Browse products (continuous)
  - Search (intermittent)
  - View details (continuous)
  - Logout (once per session)

Monitoring:
  - Memory usage (hourly)
  - CPU usage (hourly)
  - Database connections (every 15 minutes)
  - Response time (every 30 minutes)
  - Error rate (continuous)

Acceptance Criteria:
  - No memory leaks
  - Response time degradation: < 10%
  - Error rate: < 1% throughout
  - No connection pool exhaustion
```

#### Template 5: Spike Test
```yaml
Test Name: Traffic Spike Test
Test Type: Spike Test
Duration: 15 minutes

Load Pattern:
  - 0-2 min: 50 users (normal)
  - 2-4 min: Ramp to 500 users (spike)
  - 4-9 min: Maintain 500 users
  - 9-11 min: Ramp down to 50 users
  - 11-15 min: 50 users (recovery)

Monitoring:
  - Response time during spike
  - Error rate during spike
  - Recovery time after spike
  - Auto-scaling triggers

Acceptance Criteria:
  - No complete system failure
  - Error rate during spike: < 5%
  - Recovery time: < 2 minutes
  - Response time returns to normal after spike
```

### 6. JMeter Scenario Configuration

#### Thread Group Setup
```xml
<!-- Load Test -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">200</stringProp>
    <stringProp name="ThreadGroup.ramp_time">300</stringProp>
    <stringProp name="ThreadGroup.duration">1800</stringProp>
    <boolProp name="ThreadGroup.scheduler">true</boolProp>
</ThreadGroup>

<!-- Stress Test -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">500</stringProp>
    <stringProp name="ThreadGroup.ramp_time">600</stringProp>
    <stringProp name="ThreadGroup.duration">1200</stringProp>
    <boolProp name="ThreadGroup.scheduler">true</boolProp>
</ThreadGroup>

<!-- Soak Test -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">50</stringProp>
    <stringProp name="ThreadGroup.ramp_time">600</stringProp>
    <stringProp name="ThreadGroup.duration">86400</stringProp>
    <boolProp name="ThreadGroup.scheduler">true</boolProp>
</ThreadGroup>

<!-- Spike Test -->
<ThreadGroup>
    <stringProp name="ThreadGroup.num_threads">500</stringProp>
    <stringProp name="ThreadGroup.ramp_time">120</stringProp>
    <stringProp name="ThreadGroup.duration">900</stringProp>
    <boolProp name="ThreadGroup.scheduler">true</boolProp>
</ThreadGroup>
```

#### Throughput Controller
```xml
<!-- Distribute load across endpoints -->
<ThroughputController>
    <stringProp name="ThroughputController.style">1</stringProp>
    <stringProp name="ThroughputController.maxThroughput">40</stringProp>
</ThroughputController>

<HTTPSamplerProxy>
    <stringProp name="HTTPSampler.path">/api/products</stringProp>
</HTTPSamplerProxy>

<ThroughputController>
    <stringProp name="ThroughputController.style">1</stringProp>
    <stringProp name="ThroughputController.maxThroughput">30</stringProp>
</ThroughputController>

<HTTPSamplerProxy>
    <stringProp name="HTTPSampler.path">/api/products/search</stringProp>
</HTTPSamplerProxy>
```

#### Timers for Think Time
```xml
<!-- Constant Timer -->
<ConstantTimer>
    <stringProp name="ConstantTimer.delay">2000</stringProp>
</ConstantTimer>

<!-- Uniform Random Timer -->
<UniformRandomTimer>
    <stringProp name="UniformRandomTimer.delay">3000</stringProp>
    <stringProp name="UniformRandomTimer.range">1000</stringProp>
</UniformRandomTimer>

<!-- Gaussian Random Timer -->
<GaussianRandomTimer>
    <stringProp name="GaussianRandomTimer.delay">3000</stringProp>
    <stringProp name="GaussianRandomTimer.range">500</stringProp>
</GaussianRandomTimer>
```

### 7. Monitoring and Analysis

#### Server-Side Monitoring
```yaml
Application Server:
  - CPU usage
  - Memory usage
  - Thread count
  - GC frequency and duration
  - Connection pool usage

Database Server:
  - CPU usage
  - Memory usage
  - Active connections
  - Query response time
  - Slow queries
  - Deadlocks

Web Server:
  - Request rate
  - Response time
  - Error rate
  - Active connections
```

#### Client-Side Monitoring
```yaml
JMeter Metrics:
  - Response time (avg, min, max, percentiles)
  - Throughput (requests/second)
  - Error rate
  - Active threads
  - Network latency
```

#### Analysis Checklist
- [ ] Response time meets SLA
- [ ] Throughput meets requirements
- [ ] Error rate within acceptable limits
- [ ] No memory leaks detected
- [ ] No connection pool exhaustion
- [ ] Database performance acceptable
- [ ] No bottlenecks identified
- [ ] System recovers after test

### 8. Performance Test Execution

#### Pre-Test Checklist
- [ ] Test environment is production-like
- [ ] Test data is prepared
- [ ] Monitoring tools are configured
- [ ] Baseline metrics are captured
- [ ] Success criteria are defined
- [ ] Team is notified
- [ ] Rollback plan is ready

#### During Test
- [ ] Monitor real-time metrics
- [ ] Log any anomalies
- [ ] Capture screenshots/errors
- [ ] Monitor server resources
- [ ] Document any issues

#### Post-Test
- [ ] Analyze results
- [ ] Compare with baseline
- [ ] Identify bottlenecks
- [ ] Generate report
- [ ] Share findings with team
- [ ] Create action items

### 9. Performance Test Report

#### Report Structure
```markdown
# Performance Test Report

## Executive Summary
- Test type and duration
- Key findings
- Pass/Fail status
- Recommendations

## Test Configuration
- Tool and version
- Test environment
- Test data
- Load profile

## Results
### Response Time
- Average: X ms
- 95th percentile: X ms
- 99th percentile: X ms
- Max: X ms

### Throughput
- Average: X req/s
- Peak: X req/s

### Error Rate
- Total errors: X%
- HTTP errors: X%
- Timeouts: X%

### Resource Utilization
- CPU: X%
- Memory: X%
- Database: X%

## Analysis
- Bottlenecks identified
- Performance trends
- Comparison with baseline
- Root cause analysis

## Recommendations
- Optimization suggestions
- Infrastructure improvements
- Code optimizations
- Next steps
```

### 10. Best Practices

#### DO's
✓ Define clear success criteria
✓ Use production-like environment
✓ Test with realistic data volumes
✓ Monitor all system components
✓ Run tests during off-peak hours
✓ Document baseline metrics
✓ Analyze results thoroughly
✓ Share findings with team
✓ Retest after fixes
✓ Automate performance tests

#### DON'Ts
✗ Don't test in development environment
✗ Don't use unrealistic load patterns
✗ Don't ignore monitoring
✗ Don't run during peak hours
✗ Don't skip baseline comparison
✗ Don't ignore error rates
✗ Don't test without proper setup
✗ Don't forget to clean up test data

## Code Templates

### Template 1: Load Test Scenario
```yaml
Test Name: [Application] Load Test
Objective: Validate performance under expected load

Configuration:
  Users: 200 concurrent
  Duration: 30 minutes
  Ramp-up: 5 minutes
  
User Journey:
  1. Login (100%)
     - POST /api/auth/login
     - Think time: 1s
  
  2. Browse Dashboard (100%)
     - GET /dashboard
     - Think time: 3s
  
  3. View Reports (60%)
     - GET /reports
     - Think time: 5s
  
  4. Export Data (20%)
     - POST /reports/export
     - Think time: 2s

Acceptance Criteria:
  - Average response time: < 1s
  - 95th percentile: < 2s
  - Error rate: < 1%
  - Throughput: > 50 req/s
```

### Template 2: Stress Test Scenario
```yaml
Test Name: [Application] Stress Test
Objective: Find breaking point

Configuration:
  Start Users: 100
  Max Users: 1000
  Increment: 100 users every 5 minutes
  Duration: 50 minutes
  
Load Pattern:
  - 0-5 min: 100 users
  - 5-10 min: 200 users
  - 10-15 min: 300 users
  - Continue until failure or max

Success Criteria:
  - System handles 300+ users
  - Graceful degradation after breaking point
  - No data corruption
  - System recovers after test
```

### Template 3: Soak Test Scenario
```yaml
Test Name: [Application] Soak Test
Objective: Test stability over time

Configuration:
  Users: 50 concurrent
  Duration: 24 hours
  Ramp-up: 10 minutes
  
Monitoring:
  - Memory usage (hourly)
  - CPU usage (hourly)
  - Response time (every 30 min)
  - Error rate (continuous)
  - Database connections (every 15 min)

Success Criteria:
  - No memory leaks
  - Response time degradation < 10%
  - Error rate < 1%
  - No connection pool exhaustion
```

### Template 4: Spike Test Scenario
```yaml
Test Name: [Application] Spike Test
Objective: Test sudden traffic spikes

Configuration:
  Normal Load: 50 users
  Spike Load: 500 users
  Spike Duration: 10 minutes
  Total Duration: 30 minutes

Load Pattern:
  - 0-5 min: 50 users (normal)
  - 5-7 min: Ramp to 500 users
  - 7-17 min: 500 users (spike)
  - 17-19 min: Ramp to 50 users
  - 19-30 min: 50 users (recovery)

Success Criteria:
  - No complete system failure
  - Error rate during spike < 5%
  - Recovery time < 2 minutes
  - Auto-scaling triggered (if applicable)
```

## Additional Notes
- Always define clear success criteria
- Test in production-like environment
- Monitor all system components
- Document baseline metrics
- Analyze results thoroughly
- Share findings with stakeholders
- Retest after optimizations
- Consider using APM tools (New Relic, AppDynamics)
- Implement proper alerting
- Schedule tests appropriately