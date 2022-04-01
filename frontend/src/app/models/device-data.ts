export interface DeviceData {
  id: string;
  name: string;
  serialNumber: string;
  batteryLevel: number;
  alerts: SingleDeviceRecord[];
  data: SingleDeviceRecord[];
}

export interface SingleDeviceRecord {
  lat: number;
  long: number;
  temp: number | undefined;
  humidity: number | undefined;
  timestamp: string;
}

export interface SingleDeviceAlert {
  code: string;
}
