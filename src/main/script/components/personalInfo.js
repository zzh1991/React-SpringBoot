import React, { Component } from 'react';
import { Tabs, Timeline, Icon } from 'antd';
import '../styles/resume.css';

const TabPane = Tabs.TabPane;

class PersonalInfo extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="personal-info">
        <Tabs
          defaultActiveKey="education"
          tabBarStyle={{ fontWeight: 'bold', color: 'cyan' }}
        >
          <TabPane tab={'Education'} key="education">
            <div className="tab-space">
              <Timeline>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Master of Science, Zhejiang University</p>
                    <div className="education-detail">
                      <p>Control Science and Engineering</p>
                      <p>2014.9 - 2017.3</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Bacherlor, Zhejiang Sci-Tech University</p>
                    <div className="education-detail">
                      <p>Communication Engineering</p>
                      <p>2010.9 - 2014.6</p>
                    </div>
                  </div>
                </Timeline.Item>
              </Timeline>
            </div>
          </TabPane>
          <TabPane tab={'Work Experience'} key="work" >
            <div className="tab-space">
              <Timeline>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Software Development Engineer, Works Application</p>
                    <div className="education-detail">
                      <p>SRE, Operation Tools</p>
                      <p>2017.4 - now</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Intern Test Development Engineering, Tecent</p>
                    <div className="education-detail">
                      <p>Mobile QQ Zone, Protocol Resolver Tool</p>
                      <p>2016.8 - 2014.9</p>
                    </div>
                  </div>
                </Timeline.Item>
              </Timeline>
            </div>
          </TabPane>
          <TabPane tab={'Project Experience'} key="project">
            <div className="tab-space">
              <Timeline>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Moving target detectionthrough wall based on Wi-Fi signal</p>
                    <div className="education-detail">
                      <ul>
                        <li>leverage Wi-Fi signals to detect the moving target behind the wall</li>
                        <li>Utilize software defined radio equipment and Python to release the detection system</li>
                        <li>Signal processing and data analysis of the received signal by Matlab and Python</li>
                      </ul>
                      <p>2014.9 - 2016.10</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Phonemeter: Bringing EMF Detection to Smartphones</p>
                    <div className="education-detail">
                      <p>Leverage Java to design an android App to release communication between phone headset and external device</p>
                      <p>2014.7 - 2015.5</p>
                    </div>
                  </div>
                </Timeline.Item>
              </Timeline>
            </div>
          </TabPane>
          <TabPane tab="Publication" key="publication" >
            <div className="tab-space">
              <Timeline>
                <Timeline.Item>
                  <div className="education-timeline">
                    <a href="https://dl.acm.org/citation.cfm?id=2996537&dl=ACM&coll=DL&CFID=990886953&CFTOKEN=63538975"
                    >Demo Abstract: FindIt: Real-time Through-Wall Human Motion Detection Using Narrow Band SDR</a>
                    <div className="education-detail">
                      <p>Proceedings of the 14th ACM Conference on Embedded Network Sensor Systems CD-ROM. ACM, 2016: 310-311</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Phonemeter: Bringing EMF Detection to Smartphones</p>
                    <div className="education-detail">
                      <p>IEEE Communications Society 2014 Student Competition “Communications technology changing the world” Honorary certificate of appreciation</p>
                      <p>Third Author</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <a href="http://www.microwavejournal.com/articles/22406-a-wide-stopband-lowpass-filter-with-three-transmission-zeros?v=preview"
                    >A Wide Stopband Lowpass Filter with Three Transmission Zeros</a>
                    <div className="education-detail">
                      <p>Microwave Journal, 2014, 57(6)</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <a href="http://ieeexplore.ieee.org/document/6495359/"
                    >A Stopband-Expanded Low-Pass Filter with Five Transmission Zeros</a>
                    <div className="education-detail">
                      <p>2012 International Symposium on Information Science and Engineering (ISISE). IEEE, 2012: 335-338</p>
                    </div>
                  </div>
                </Timeline.Item>
              </Timeline>
            </div>
          </TabPane>
          <TabPane tab={'Awards'} key="award">
            <div className="tab-space">
              <Timeline>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Merit Student, Excellent graduate students</p>
                    <div className="education-detail">
                      <p>2016</p>
                    </div>
                  </div>
                </Timeline.Item>
                <Timeline.Item>
                  <div className="education-timeline">
                    <p>Zhejiang University “Five” teaching team construction scholarship</p>
                    <div className="education-detail">
                      <p>2015</p>
                    </div>
                  </div>
                </Timeline.Item>
              </Timeline>
              <Timeline.Item>
                <div className="education-timeline">
                  <p>The first prize of the Challenge Cup, Zhejiang Province</p>
                  <div className="education-detail">
                    <p>2013</p>
                  </div>
                </div>
              </Timeline.Item>
              <Timeline.Item>
                <div className="education-timeline">
                  <p>Jinfuchun innovation and entrepreneurship scholarship</p>
                  <div className="education-detail">
                    <p>2013</p>
                  </div>
                </div>
              </Timeline.Item>
              <Timeline.Item>
                <div className="education-timeline">
                  <p>First-class Scholarship</p>
                  <div className="education-detail">
                    <p>2011, 2012</p>
                  </div>
                </div>
              </Timeline.Item>
            </div>
          </TabPane>
        </Tabs>
      </div>
    );
  }
}

export default PersonalInfo;
