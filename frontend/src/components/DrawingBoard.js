import React, { useRef } from "react";
import ReactDOM from "react-dom";
import CanvasDraw from "react-canvas-draw";

const DrawingBoard = ({ onSave }) => {
  const canvasRef = useRef(null);

const saveDrawing = () => {
  if (!canvasRef.current) return;

  const dataUrl = canvasRef.current.getDataURL("png");
  fetch(dataUrl)
    .then((res) => res.blob())
    .then((blob) => {
      if (onSave) {
        onSave(blob); // pass blob to parent w onSave
      }
    });
};

  // Clear the drawing
  const handleClear = () => {
    canvasRef.current.clear();
  };

  return (
    <div className="flex flex-col items-center space-y-4">
      <h2 className="text-2xl font-bold">Draw Your Art</h2>
      <CanvasDraw
        ref={canvasRef}
        brushColor="#000"
        brushRadius={3}
        lazyRadius={1}
        canvasWidth={600}
        canvasHeight={400}
        hideGrid
        className="border-2 border-gray-400 rounded-md shadow-md"
      />
      <div className="flex space-x-4">
        <button
          onClick={saveDrawing}
          className="bg-green-600 px-4 py-2 rounded text-white hover:bg-green-700"
        >
          Save
        </button>
        <button
          onClick={handleClear}
          className="bg-red-600 px-4 py-2 rounded text-white hover:bg-red-700"
        >
          Clear
        </button>
      </div>
    </div>
  );
};

export default DrawingBoard