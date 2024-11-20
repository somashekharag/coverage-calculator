import React, { useEffect, useState } from 'react';
import { Chart as ChartJS, ArcElement, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';
import { Pie, Bar } from 'react-chartjs-2';
import ChartDataLabels from 'chartjs-plugin-datalabels';
import Modal from 'react-modal';

// Modal Styles
const modalStyles = {
    content: {
      top: '50%',
      left: '50%',
      right: 'auto',
      bottom: 'auto',
      marginRight: '-50%',
      transform: 'translate(-50%, -50%)',
      maxHeight: '80%',
      width: '80%',
      overflowY: 'auto',
      borderRadius: '8px',
    },
  };

const CoverageMetrics = () => {
  const[origin, setOrigin]=useState('');
  const [metrics, setMetrics] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [modalData, setModalData] = useState([]);
  const [modalTitle, setModalTitle] = useState('');

// Register required Chart.js components
ChartJS.register(ArcElement, Tooltip, Legend, BarElement, CategoryScale, LinearScale);


  useEffect(() => {
    console.log(process.env.NODE_ENV);
     const devUrl = "http://localhost:8080";
     const prodUrl = window.location.origin;
     const isDevelopment = process.env.NODE_ENV === 'development'
     setOrigin(isDevelopment? devUrl : prodUrl)
   }, []);

   useEffect(() => {
    if(!origin) return;
    fetch(`${origin}/api/coverage`)
      .then(response => response.json())
      .then(data => {
        console.log('Coverage Metrics:', data); // Log API response
        setMetrics(data);
      })
      .catch(error => console.error('Error fetching coverage metrics:', error));
  }, [origin]);

  const openModal = (title, data) => {
    setModalTitle(title);
    setModalData(data);
    setShowModal(true);
  };

  const closeModal = () => setShowModal(false);

  if (!metrics || !metrics.totalTestCases) {
    return <div className="text-center py-10 text-xl">Loading...</div>;
  }

  const pendingAutomationCount = metrics.automatableTestCases - metrics.automatedTestCases;

  const automationCoveragePercentage = (
    (metrics.automatedTestCases / metrics.automatableTestCases) *
    100
  ).toFixed(2);

  const automationGapPercentage = (100 - automationCoveragePercentage).toFixed(2);

  const pieData = {
    labels: ['Automated', 'Pending Automation'],
    datasets: [
      {
        data: [metrics.automatedTestCases, pendingAutomationCount],
        backgroundColor: ['#4CAF50', '#FF5722'],
        hoverBackgroundColor: ['#45A049', '#E64A19'],
      },
    ],
  };

  const barData = {
    labels: ['Total Test Cases', 'Automatable', 'Automated', 'Non-Automatable', 'Pending Automation'],
    datasets: [
      {
        label: 'Test Cases',
        data: [
          metrics.totalTestCases,
          metrics.automatableTestCases,
          metrics.automatedTestCases,
          metrics.nonAutomatableTestCases,
          pendingAutomationCount,
        ],
        backgroundColor: ['#2196F3', '#FFC107', '#4CAF50', '#9E9E9E', '#FF5722'],
      },
    ],
  };

  const pieOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      datalabels: {
        formatter: (value, context) => `${value}`,
        color: '#fff',
        font: {
          weight: 'bold',
        },
      },
    },
  };

  const barOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top',
      },
      datalabels: {
        anchor: 'end',
        align: 'end',
        formatter: (value, context) => `${value}`,
        color: '#000',
        font: {
          size: 12,
        },
      },
    },
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  return (
    <div className="p-8 max-w-4xl mx-auto">
      <h1 className="text-2xl font-bold text-gray-800 mb-6">Test Coverage Metrics</h1>

      {/* Metrics Summary */}
      <div className="mb-6 grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="p-4 bg-white shadow-lg rounded-lg border border-gray-200">
          <h2 className="text-lg font-semibold text-gray-700 mb-2">Automation Coverage</h2>
          <p className="text-3xl font-bold text-green-500">{automationCoveragePercentage}%</p>
        </div>
        <div className="p-4 bg-white shadow-lg rounded-lg border border-gray-200">
          <h2 className="text-lg font-semibold text-gray-700 mb-2">Automation Gap</h2>
          <p className="text-3xl font-bold text-red-500">{automationGapPercentage}%</p>
        </div>
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <div className="p-4 bg-white shadow-lg rounded-lg border border-gray-200">
          <h2 className="text-lg font-semibold text-gray-700 mb-4 text-center">Automation Coverage</h2>
          <Pie data={pieData} options={pieOptions} />
        </div>

        <div className="p-4 bg-white shadow-lg rounded-lg border border-gray-200">
          <h2 className="text-lg font-semibold text-gray-700 mb-4 text-center">Test Case Distribution</h2>
          <Bar data={barData} options={barOptions} />
        </div>
      </div>

      {/* Links to Modals */}
      <div className="mt-8">
        <button
          onClick={() => openModal('Untagged Scenarios', metrics.untaggedScenarios)}
          className="text-blue-500 hover:underline mr-4"
        >
          View Untagged Scenarios
        </button>
        <button
          onClick={() => openModal('Pending Automation Test Cases', metrics.pendingAutomationTestCases)}
          className="text-blue-500 hover:underline"
        >
          View Pending Automation Test Cases
        </button>
      </div>

      {/* Modal */}
      <Modal isOpen={showModal} onRequestClose={closeModal} style={modalStyles} contentLabel="Details Modal">
        <h2 className="text-lg font-bold mb-4">{modalTitle}</h2>
        <button onClick={closeModal} className="text-red-500 hover:underline mb-4">
          Close
        </button>
        <ul className="list-disc pl-6">
          {modalData.map((item, index) => (
            <li key={index} className="mb-2">{item}</li>
          ))}
        </ul>
      </Modal>
    </div>
  );
};

export default CoverageMetrics;
