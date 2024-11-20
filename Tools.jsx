import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { FaChartPie, FaTools } from 'react-icons/fa';
const Tools = () => {
    return (
        <div className="flex flex-col items-center justify-center h-full bg-gray-100">
          {/* <h1 className="text-3xl font-bold text-gray-800 mb-6">Tools Dashboard</h1> */}
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-6 w-full max-w-4xl">
            {/* Coverage Metrics Card */}
            <Link
              to="/tools/coverage-metrics"
              className="flex items-center justify-center bg-white p-6 rounded-lg shadow-md hover:shadow-lg hover:bg-gray-50 transition duration-300"
            >
              <div className="flex flex-col items-center">
                <FaChartPie className="text-4xl text-blue-500 mb-4" />
                <h2 className="text-xl font-semibold text-gray-800">Coverage Metrics</h2>
                <p className="text-gray-600 mt-2 text-center">
                  View detailed automation coverage statistics and insights.
                </p>
              </div>
            </Link>
    
            {/* Feature Analyzer Card */}
            <Link
              to="/tools/feature-analyzer"
              className="flex items-center justify-center bg-white p-6 rounded-lg shadow-md hover:shadow-lg hover:bg-gray-50 transition duration-300"
            >
              <div className="flex flex-col items-center">
                <FaTools className="text-4xl text-green-500 mb-4" />
                <h2 className="text-xl font-semibold text-gray-800">Feature Analyzer</h2>
                <p className="text-gray-600 mt-2 text-center">
                  Analyze feature files for completeness and consistency.
                </p>
              </div>
            </Link>
          </div>
        </div>
      );
};

export default Tools;
